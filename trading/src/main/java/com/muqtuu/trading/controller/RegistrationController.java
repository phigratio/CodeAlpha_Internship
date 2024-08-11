package com.muqtuu.trading.controller;

import com.muqtuu.trading.dtos.UserRegistrationDto;
import com.muqtuu.trading.entities.*;
import com.muqtuu.trading.event.OnRegistrationCompleteEvent;
import com.muqtuu.trading.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "registration";
    }

    @PostMapping("/register")
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto userDto, Model model) {
        User registered = userService.registerNewUserAccount(userDto);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered));
        return "redirect:/verify-email";
    }

    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token, Model model) {
        String result = userService.validateVerificationToken(token);
        if (result.equals("valid")) {
            model.addAttribute("message", "Your account has been verified successfully.");
            return "verified";
        } else {
            model.addAttribute("message", "Invalid verification token.");
            return "verify-email";
        }
    }
}
