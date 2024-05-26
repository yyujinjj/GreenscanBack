package com.io.greenscan.service;

import com.io.greenscan.dto.request.ExchangeRequestDto;
import com.io.greenscan.entity.ExchangerTicket;
import com.io.greenscan.entity.User;
import com.io.greenscan.entity.UserExchanger;
import com.io.greenscan.repository.ExchangerTicketRepository;
import com.io.greenscan.repository.UserExchangerRepository;
import com.io.greenscan.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExchangerService {


    private final UserRepository userRepository;
    private final ExchangerTicketRepository exchangerTicketRepository;
    private final UserExchangerRepository userExchangerRepository;
    private final JwtService jwtService;

    public ExchangerService(UserRepository userRepository, ExchangerTicketRepository exchangerTicketRepository, UserExchangerRepository userExchangerRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.exchangerTicketRepository = exchangerTicketRepository;
        this.userExchangerRepository = userExchangerRepository;
        this.jwtService = jwtService;
        log.info("또르르");
    }


    @Transactional
    public ResponseEntity<String> processExchangeRequest(ExchangeRequestDto requestDto, String userEmail) {

        // JWT를 사용한 사용자 인증
        User user =userRepository.findByEmail(userEmail).orElse(null);
        if(user == null) {
            log.info("하나");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }
        if (!requestDto.isConfirm()) {
            log.info("둘");
            return ResponseEntity.ok("이전 화면으로 돌아갑니다.");
        }

        if (!authenticateUser(requestDto.getEmail(), requestDto.getPassword())) {
            log.info("셋");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("본인 인증 실패");
        }


        ExchangerTicket exchangerTicket = getExchangerTicketById(requestDto.getExchangerTicketId());
        if (exchangerTicket == null) {
            log.info("넷");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 교환권을 찾을 수 없습니다.");
        }

        Long requiredMileage = exchangerTicket.getExchangerTicketPrice();
        if (!deductMileage(user, requiredMileage)) {
            log.info("다섯");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("마일리지가 부족합니다.");
        }

        UserExchanger userExchanger = createAndSaveUserExchanger(user, exchangerTicket);

        // user 객체에 userExchanger 설정
        user.setUserExchanger(userExchanger);
        userRepository.save(user);

        return ResponseEntity.ok("본인 인증 성공\n" + "사용자 정보: " + user.toString() + "\n" + "교환권 정보: " + exchangerTicket.toString());
    }



    private boolean authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email).orElse(null);
        return user != null && user.getPassword().equals(password);
    }


    private ExchangerTicket getExchangerTicketById(Long ticketId) {
        return exchangerTicketRepository.findById(ticketId).orElse(null);
    }

    private boolean deductMileage(User user, Long requiredMileage) {
        if (user.getMileage() >= requiredMileage) {
            log.info("뿅");
            user.setMileage(user.getMileage() - requiredMileage);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    private UserExchanger createAndSaveUserExchanger(User user, ExchangerTicket exchangerTicket) {
        log.info("두둥탁");
        UserExchanger userExchanger = new UserExchanger();
        userExchanger.setUser(user);
        userExchanger.setExchangerTicket(exchangerTicket);
        userExchangerRepository.save(userExchanger);
        return userExchanger;
    }
}

