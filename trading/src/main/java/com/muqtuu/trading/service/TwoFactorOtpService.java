package com.muqtuu.trading.service;

import com.muqtuu.trading.entities.TwoFactorOTP;
import com.muqtuu.trading.entities.User;

import java.util.Optional;

public interface TwoFactorOtpService {

    TwoFactorOTP createTwoFactorOtp(Optional<User> user, String otp, String jwt);
    TwoFactorOTP findByUser(Long userId);
    TwoFactorOTP findById(String id);
    boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOtp,String otp);
    void deleteTwoFactorOtp(TwoFactorOTP twoFactorOtp);

}
