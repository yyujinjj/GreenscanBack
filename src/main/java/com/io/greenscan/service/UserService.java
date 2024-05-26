package com.io.greenscan.service;


import com.io.greenscan.dto.request.UpdateProfileRequestDto;
import com.io.greenscan.dto.request.UserLoginRequestDTO;
import com.io.greenscan.dto.request.UserSignUpRequestDTO;
import com.io.greenscan.dto.response.UserInfoDTO;
import com.io.greenscan.dto.response.UserSignUpResponseDTO;
import com.io.greenscan.entity.User;
import com.io.greenscan.exception.*;
import com.io.greenscan.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService {



    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }


    public UserSignUpResponseDTO signup(UserSignUpRequestDTO userSignUpRequestDTO) {
        // 비밀번호 확인
        if (!userSignUpRequestDTO.getPassword().equals(userSignUpRequestDTO.getConfirmPassword())) {
            throw new PasswordsDoNotMatchException("비밀번호와 재입력한 비밀번호가 일치하지 않습니다.");
        }

        // 추천인 아이디 유효성 검사
        if (userSignUpRequestDTO.getReferralId() != null && !isValidReferralId(userSignUpRequestDTO.getReferralId())) {
            throw new InvalidReferralIdException("추천인 아이디가 올바르지 않습니다.");
        }

        // 이미 등록된 이메일인지 확인
        Optional<User> findUser = userRepository.findByEmail(userSignUpRequestDTO.getEmail());
        if (findUser.isEmpty()) {
            // 회원가입 처리
            User user = new User(userSignUpRequestDTO);
            User savedUser = userRepository.save(user);

            // 추천인 처리
            if (userSignUpRequestDTO.getReferralId() != null) {
                processReferral(userSignUpRequestDTO.getReferralId());
            }

            return new UserSignUpResponseDTO(savedUser);
        }
        throw new EmailAlreadyExistsException("이미 등록된 이메일입니다.");
    }



    public void processReferral(String referralId) {
        // 추천인 정보 조회
        Optional<User> referralUserOptional = userRepository.findByEmail(referralId);
        if (referralUserOptional.isPresent()) {
            User referralUser = referralUserOptional.get();

            // 추천인의 마일리지 증가
            referralUser.setMileage(referralUser.getMileage() + 300);
            userRepository.save(referralUser);
        } else {
            throw new InvalidReferralIdException("유효하지 않은 추천인 아이디입니다.");
        }
    }

    public boolean isValidReferralId(String referralId) {
        // 데이터베이스에서 추천인 아이디 조회
        // 존재하는지 여부를 판단하여 반환
        return userRepository.existsByEmail(referralId);
    }



    // 로그인 메서드
    public String login(UserLoginRequestDTO userLoginRequestDTO) {
        // 이메일로 사용자 조회


        Optional<User> userOptional = userRepository.findByEmail(userLoginRequestDTO.getEmail());

        // 사용자가 존재하는지 확인
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("존재하지 않는 아이디입니다.");
        }

        User user = userOptional.get();

        // 비밀번호가 일치하는지 확인
        if (!user.getPassword().equals(userLoginRequestDTO.getPassword())) {
            throw new InvalidPasswordException("비밀번호를 잘못 입력하였습니다.");
        }

        // 로그인 성공 시 JWT 토큰 생성
        String token = jwtService.createToken(user.getEmail());
        return token;
    }

    // 사용자 정보 조회 메서드
    public UserInfoDTO getUserInfo(String token) {
        log.info("정보가 들어오긴 하나???");
        // 토큰에서 사용자 이메일 추출
        String email = jwtService.getUserEmail(token);

        // 토큰에 포함된 사용자 이메일을 로그에 출력하여 디버깅
        log.info("토큰에 포함된 사용자 이메일: {}", email);

        // DB에서 이메일로 사용자 정보 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        // 조회한 사용자 정보로 UserInfoDTO 생성하여 반환
        return new UserInfoDTO(user.getUserName(), user.getPhoneNumber(), user.getUserExchangers(), user.getMileage(), user.getEmail(), null);

    }

    private User validateUser(String password, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String email = jwtService.getUserEmail(token);
        Optional<User> userOptional = userRepository.findByEmailAndPassword(email, password);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new UserNotFoundException("사용자 인증에 실패했습니다.");
        }
    }


    @Transactional
    public UserInfoDTO updateUserProfile(UpdateProfileRequestDto requestDto, HttpServletRequest request) {
        log.info("updateUserProfile Service");

        // 토큰 확인
        String token = request.getHeader("Authorization");
        String email = jwtService.getUserEmail(token);
        Optional<User> userOptional = userRepository.findByEmail(email);
        // 사용자 인증 확인
//        User findUser = validateUser(requestDto.getPassword(), request);
        if (userOptional.isEmpty()) {
            throw new RuntimeException();
        }
        User findUser = userOptional.get();

        // 사용자 정보 업데이트
//        if (requestDto.getEmail() != null) {
//            findUser.setEmail(requestDto.getEmail());
//        }
        if (requestDto.getUsername() != null) {
            findUser.setUserName(requestDto.getUsername());
        }
        if (requestDto.getPassword() != null) {
            findUser.setPassword(requestDto.getPassword());
        }
        if (requestDto.getPhoneNumber() != null) {
            findUser.setPhoneNumber(requestDto.getPhoneNumber());
        }

        // 변경된 정보 저장
        userRepository.save(findUser);

        log.info("사용자 프로필 업데이트 성공");
        return new UserInfoDTO(findUser);


//    // 마일리지 정보 조회 메서드
//    public MileageInfoDTO getMileageInfo(String token) {
//        // 토큰에서 사용자 이메일 추출
//        String email = jwtService.getUserEmail(token);
//
//        // DB에서 이메일로 사용자 정보 조회
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
//
//        // 마일리지 정보를 MileageInfoDTO로 변환하여 반환
//        return new MileageInfoDTO(user.getMileage());
//    }
    }
}


