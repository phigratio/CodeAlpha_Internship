package com.muqtuu.trading.repositories;


import com.muqtuu.trading.entities.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepo extends JpaRepository<VerificationCode, Integer> {

    public VerificationCode findByUserId(Long userId);
}
