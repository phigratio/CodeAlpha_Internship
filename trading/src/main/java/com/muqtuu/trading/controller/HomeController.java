package com.muqtuu.trading.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {

	
	
	@GetMapping
	public String Home()
	{
		return "Welcome To Stock Platform";
	}

	@GetMapping("/api")
	public String secure()
	{
		return "Welcome To Stock Platform";
	}
}
