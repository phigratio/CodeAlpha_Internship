package com.muqtuu.trading.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muqtuu.trading.entities.User;

public interface UserRepo extends JpaRepository<User, Long> {

    User findByEmail(String email);



}
