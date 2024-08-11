package com.muqtuu.trading.service;

import com.muqtuu.trading.config.JwtProvider;
import com.muqtuu.trading.domain.VerificationType;
import com.muqtuu.trading.dtos.UserRegistrationDto;
import com.muqtuu.trading.entities.TwoFactorAuth;
import com.muqtuu.trading.entities.User;
import com.muqtuu.trading.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EmailService emailService;

    public User registerNewUserAccount(UserRegistrationDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        user.setEnabled(false);
        userRepo.save(user);

        // Generate verification token and send email
        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        userRepo.save(user);

        String confirmationUrl = "http://localhost:8080/verify-email?token=" + token;
        emailService.sendEmail(user.getEmail(), "Email Verification", "Click the link to verify your email: " + confirmationUrl);

        return user;
    }

    @Override
    public Optional<User> findUserProfileByJwt(String jwt) throws Exception {

        String email= JwtProvider.getEmailFromToken(jwt);

        Optional<User> user = userRepo.findByEmail(email);

        if(user==null)
        {
            throw new Exception("user not found");
        }
        return user;
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws Exception {
        Optional<User> user = userRepo.findByEmail(email);

        if(user==null)
        {
            throw new Exception("user not found");
        }
        return user;
    }

    public String validateVerificationToken(String token) {
        User user = userRepo.findByVerificationToken(token).orElse(null);
        if (user == null) {
            return "invalid";
        }

        user.setEnabled(true);
        userRepo.save(user);
        return "valid";
    }

    @Override
    public User findUserById(Long userId) throws Exception {

        Optional<User> user = userRepo.findById(userId);
        if(user.isEmpty())
        {
            throw new Exception("user not found");
        }
        return user.get();

    }

    @Override
    public User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo, User user) {
        TwoFactorAuth twoFactorAuth=new TwoFactorAuth();
        twoFactorAuth.setEnabled(true);
        twoFactorAuth.setSendTo(verificationType);
        user.setTwoFactorAuth(twoFactorAuth);

        return userRepo.save(user);
    }


    @Override
    public User updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        return userRepo.save(user);
    }
}
