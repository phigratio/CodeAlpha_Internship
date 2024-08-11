package com.muqtuu.trading.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.muqtuu.trading.domain.USER_ROLE;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String fullName;
	@Column(unique = true,nullable = false)
	private String email;

	@Column(nullable = false)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	@Embedded
	private TwoFactorAuth twoFactorAuth=new TwoFactorAuth();

	private USER_ROLE role=USER_ROLE.ROLE_CUSTOMER;


	@Column(nullable = false)
	private boolean enabled;

	private String verificationToken;


	public String getUsername() {
		return fullName;
	}

	public boolean isAccountVerified() {
		return verificationToken != null;
	}

	public Role[] getRoles() {
		return null;
	}
}


