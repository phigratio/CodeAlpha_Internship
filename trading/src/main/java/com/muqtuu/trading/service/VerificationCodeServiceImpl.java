package com.muqtuu.trading.service;


import com.muqtuu.trading.domain.VerificationType;
import com.muqtuu.trading.entities.User;
import com.muqtuu.trading.entities.VerificationCode;
import com.muqtuu.trading.repositories.VerificationCodeRepo;
import com.muqtuu.trading.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Autowired
    private VerificationCodeRepo verificationCodeRepo;

    @Override
    public VerificationCode sendVerificationCode(User user, VerificationType verificationType) {
        VerificationCode verificationCode1=new VerificationCode();
        verificationCode1.setOtp(OtpUtils.generateOtp());
        verificationCode1.setVerificationType(verificationType);
        verificationCode1.setUser(user);

        return verificationCodeRepo.save(verificationCode1);
    }

    @Override
    public VerificationCode getVerificationCodeById(Long id) throws Exception {
        Optional<VerificationCode> verificationCode=
                verificationCodeRepo.findById(Math.toIntExact(id));

        if(verificationCode.isPresent()){
            return verificationCode.get();
        }

        throw new Exception("verification code not found");
    }

    @Override
    public VerificationCode getVerificationCodeByUser(Long userId) throws Exception {
            return verificationCodeRepo.findByUserId(userId);
    }

    @Override
        public void deleteVerificationCodeById(VerificationCode verificationCode) {
            verificationCodeRepo.delete(verificationCode);
        }
}
