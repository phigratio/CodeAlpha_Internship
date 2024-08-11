package com.muqtuu.trading.service;

import com.muqtuu.trading.domain.VerificationType;
import com.muqtuu.trading.dtos.UserRegistrationDto;
import com.muqtuu.trading.entities.User;

import java.util.Optional;

public interface UserService {

    public User findUserProfileByJwt(String jwt) throws Exception;
    public Optional<User> findUserByEmail(String email) throws Exception;
    public User findUserById(Long userId) throws Exception;

    public User enableTwoFactorAuthentication(VerificationType verificationType,String sendTo ,User user);

    User updatePassword(User user,String newPassword);

    String validateVerificationToken(String token);

    User registerNewUserAccount(UserRegistrationDto userDto);
}
