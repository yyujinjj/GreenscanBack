package com.io.greenscan.service;

import com.io.greenscan.entity.User;
import com.io.greenscan.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Transactional
    public String completeGameLevel(String token, int completedLevel) {
        String userEmail = jwtService.getUserEmail(token); // Extract user email from the token
        if (userEmail == null) {
            return "토큰이 유효하지 않습니다.";
        }

        User user = userRepository.findByEmail(userEmail).orElse(null);
        if (user == null) {
            return "사용자를 찾을 수 없습니다.";
        }

        int baseMileage = 5; // 기본적으로 Level-1 성공 시 제공되는 마일리지
        int additionalMileagePerLevel = 5; // 레벨이 높아질 때마다 추가되는 마일리지

        // 요구사항에 따른 마일리지 계산
        int earnedMileage = baseMileage + (additionalMileagePerLevel * (completedLevel - 1));
        user.setMileage(user.getMileage() + earnedMileage);

        userRepository.save(user);
        return "게임 레벨 완료. 적립된 마일리지: " + earnedMileage;
    }
}
