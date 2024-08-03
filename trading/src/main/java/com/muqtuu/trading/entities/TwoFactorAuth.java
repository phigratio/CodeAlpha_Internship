package com.muqtuu.trading.entities;

import com.muqtuu.trading.domain.VerificationType;

import lombok.Data;

@Data
public class TwoFactorAuth {

	private boolean isEnabled=false;
	private VerificationType sendTo; 
}
