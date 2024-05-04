package com.io.greenscan.service;

import com.io.greenscan.dto.request.UserSignUpRequestDTO;
import com.io.greenscan.dto.response.UserSignUpResponseDTO;
import com.io.greenscan.entity.User;
import com.io.greenscan.exception.EmailAlreadyExistsException;
import com.io.greenscan.exception.InvalidReferralIdException;
import com.io.greenscan.exception.PasswordsDoNotMatchException;
import com.io.greenscan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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
            return new UserSignUpResponseDTO(savedUser);
        }
        throw new EmailAlreadyExistsException("이미 등록된 이메일입니다.");
    }

    private boolean isValidReferralId(String referralId) {
        // 데이터베이스에서 추천인 아이디 조회
        // 존재하는지 여부를 판단하여 반환
        return userRepository.existsByEmail(referralId);
    }
}


