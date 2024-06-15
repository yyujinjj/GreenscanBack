package com.io.greenscan.repository;

import com.io.greenscan.entity.User;
import com.io.greenscan.entity.UserExchanger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserExchangerRepository extends JpaRepository<UserExchanger, Long> {
    Optional<UserExchanger> findByUser(User user);
}
