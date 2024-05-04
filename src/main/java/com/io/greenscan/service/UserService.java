package com.io.greenscan.service;

import com.io.greenscan.controller.Greenscan.PasswordsDoNotMatchException;
import com.io.greenscan.dto.request.UserSignUpRequestDTO;
import com.io.greenscan.dto.response.UserSignUpResponseDTO;
import com.io.greenscan.entity.User;
import com.io.greenscan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserSignUpResponseDTO signup(UserSignUpRequestDTO userSignUpRequestDTO) {
        //비밀번호 확인
        if(!userSignUpRequestDTO.getPassword().equals(userSignUpRequestDTO.getConfirmPassword())){
            throw new PasswordsDoNotMatchException("비밀번호와 재입력한 비밀번호가 일치하지 않습니다.");
        }

        Optional<User> findUser = userRepository.findByEmail(userSignUpRequestDTO.getEmail());

        if (findUser.isEmpty()) {
            User user = new User(userSignUpRequestDTO);
            User savedUser = userRepository.save(user);
            return new UserSignUpResponseDTO(savedUser);
        }
        //Todo : custom으로 수정해야함.
        throw new RuntimeException();
    }
}

