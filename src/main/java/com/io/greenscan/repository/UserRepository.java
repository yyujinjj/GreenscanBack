package com.io.greenscan.repository;

import com.io.greenscan.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email); //exist로 나오게되면 뒤에 있는 값으로 해서 있는 지 없는 지 true/false로 하여 => boolean값으로 나타냄

    Optional<User> findByEmailAndPassword(String email, String password);
}
