package com.muqtuu.trading.service;

import com.muqtuu.trading.domain.VerificationType;
import com.muqtuu.trading.entities.User;
import com.muqtuu.trading.entities.VerificationCode;

public interface VerificationCodeService {

    VerificationCode sendVerificationCode(User user, VerificationType verificationType);
    VerificationCode getVerificationCodeById(Long id) throws Exception;
    VerificationCode getVerificationCodeByUser(Long userId) throws Exception;
    void deleteVerificationCodeById(VerificationCode verificationCode);

}
