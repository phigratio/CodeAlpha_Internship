package com.muqtuu.trading.controller;

import com.muqtuu.trading.config.JwtProvider;
import com.muqtuu.trading.entities.TwoFactorOTP;
import com.muqtuu.trading.repositories.TwoFactorOtpRepo;
import com.muqtuu.trading.response.AuthResponse;
import com.muqtuu.trading.service.CustomUserDetailsService;
import com.muqtuu.trading.service.EmailService;
import com.muqtuu.trading.service.TwoFactorOtpService;
import com.muqtuu.trading.utils.OtpUtils;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.muqtuu.trading.entities.User;
import com.muqtuu.trading.repositories.UserRepo;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private TwoFactorOtpService twoFactorOtpService;
    @Autowired
    private EmailService emailService;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception {

		User isEmailExist=userRepo.findByEmail(user.getEmail());

		if(isEmailExist!=null)
		{
			throw new Exception("Email already exist");
		}

		User newUser= new User();
		newUser.setEmail(user.getEmail());
		newUser.setPassword(user.getPassword());
		newUser.setFullName(user.getFullName());


		User savedUser= userRepo.save(newUser);

		Authentication auth=new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());

		SecurityContextHolder.getContext().setAuthentication(auth);

		String jwt= JwtProvider.generateToken(auth);


		AuthResponse res=new AuthResponse();
		res.setJwt(jwt);
		res.setStatus(true);
		res.setMessage("register successful");

		return new ResponseEntity<>(res, HttpStatus.CREATED);


	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> login(@RequestBody User user) throws Exception {

		User isEmailExist=userRepo.findByEmail(user.getEmail());


		String userName=user.getEmail();
		String password=user.getPassword();



		Authentication auth=auhthenticate(userName,password);

		SecurityContextHolder.getContext().setAuthentication(auth);

		String jwt= JwtProvider.generateToken(auth);

		User authUser=userRepo.findByEmail(userName);
		if(user.getTwoFactorAuth().isEnabled())
		{
			AuthResponse res=new AuthResponse();
			res.setMessage("Two-Factor Authentication Enabled");
			res.setTwoFactorAuthEnabled(true);
			String otp= OtpUtils.generateOtp();

			TwoFactorOTP oldTwoFactorOTP=twoFactorOtpService.findByUser(authUser.getId());
			if(oldTwoFactorOTP!=null)
			{
				twoFactorOtpService.deleteTwoFactorOtp(oldTwoFactorOTP);
			}

			TwoFactorOTP newTwoFactorOTP=twoFactorOtpService.createTwoFactorOtp(
					authUser,otp,jwt);

			emailService.sendVerificationOtpEmail(userName,otp);
			res.setSession(newTwoFactorOTP.getId());

			return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
		}

		AuthResponse res=new AuthResponse();
		res.setJwt(jwt);
		res.setStatus(true);
		res.setMessage("login successful");

		return new ResponseEntity<>(res, HttpStatus.CREATED);


	}

	private Authentication auhthenticate(String userName, String password) {

		UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);

		if (userDetails == null) {
			throw new BadCredentialsException("Invalid username ");
		}

		if (!password.equals(userDetails.getPassword()))
		{
			throw new BadCredentialsException("Passwords do not match");
		}

		return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
	}

	@PostMapping("/two-factor/otp/{otp}")
	public ResponseEntity<AuthResponse> verifySignInOtp(@PathVariable String otp,
														 @RequestParam String id) throws Exception {
		TwoFactorOTP twoFactorOTP=twoFactorOtpService.findById(id);

		if(twoFactorOtpService.verifyTwoFactorOtp(twoFactorOTP,otp))
		{
			AuthResponse res=new AuthResponse();
			res.setMessage("Two-Factor Authentication Verified");
			res.setTwoFactorAuthEnabled(true);
			res.setJwt(twoFactorOTP.getJwt());
			return new ResponseEntity<>(res,HttpStatus.OK);
		}

		throw new Exception("Invalid otp");
	}

}
