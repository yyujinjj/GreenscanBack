package com.io.greenscan.service;

import com.io.greenscan.dto.request.ExchangeRequestDto;
import com.io.greenscan.dto.response.ExchangeResponseDto;
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

@Service
@Slf4j
public class ExchangerService {

    private final UserRepository userRepository;
    private final ExchangerTicketRepository exchangerTicketRepository;
    private final UserExchangerRepository userExchangerRepository;
    private final JwtService jwtService;

    public ExchangerService(UserRepository userRepository, ExchangerTicketRepository exchangerTicketRepository,
                            UserExchangerRepository userExchangerRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.exchangerTicketRepository = exchangerTicketRepository;
        this.userExchangerRepository = userExchangerRepository;
        this.jwtService = jwtService;
        log.info("셋");
    }

    @Transactional
    public ResponseEntity<ExchangeResponseDto> processExchangeRequest(ExchangeRequestDto requestDto, String token) {
        String userEmail = jwtService.getUserEmail(token);
        log.info("넷");
        if (userEmail == null) {
            log.error("Authentication failed or token invalid.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExchangeResponseDto("Authentication failed or token invalid.", null, null));
        }

        User user = userRepository.findByEmail(userEmail).orElse(null);
        if (user == null) {
            log.error("User not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExchangeResponseDto("User not found.", null, null));
        }

        ExchangerTicket exchangerTicket = getExchangerTicketById(requestDto.getExchangerTicketId());
        if (exchangerTicket == null) {
            log.error("Exchanger ticket not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExchangeResponseDto("Exchanger ticket not found.", null, null));
        }

        if (!requestDto.isConfirm() || !deductMileage(user, exchangerTicket.getExchangerTicketPrice())) {
            log.error("Operation not confirmed or insufficient mileage.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExchangeResponseDto("Operation not confirmed or insufficient mileage.", null, null));
        }

        createAndSaveUserExchanger(user, exchangerTicket);

        log.info("Exchange successful: User={}, ExchangerTicket={}", user, exchangerTicket);

        return ResponseEntity.ok(new ExchangeResponseDto("Mobile exchange ticket purchased successfully.", user.getUserName(), exchangerTicket.getExchangerTicketName()));
    }

    private ExchangerTicket getExchangerTicketById(Long ticketId) {
        return exchangerTicketRepository.findById(ticketId).orElse(null);
    }

    private boolean deductMileage(User user, Long requiredMileage) {
        if (user.getMileage() >= requiredMileage) {
            user.setMileage(user.getMileage() - requiredMileage);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    private UserExchanger createAndSaveUserExchanger(User user, ExchangerTicket exchangerTicket) {
        UserExchanger userExchanger = new UserExchanger();
        userExchanger.setUser(user);
        userExchanger.setExchangerTicket(exchangerTicket);
        userExchangerRepository.save(userExchanger);
        return userExchanger;
    }
}
