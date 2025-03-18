package com.carPooling.dto;

import com.carPooling.entity.enums.Role;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	
	@NotNull
	private String username;
	@NotNull
	private String name;
	@NotNull
	private String password;
	@NotNull
	private Role role;
	@NotNull
	private String phoneNo; 
	

}
