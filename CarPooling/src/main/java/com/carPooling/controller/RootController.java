package com.carPooling.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carPooling.dto.LoginDTO;
import com.carPooling.dto.ResponseDTO;
import com.carPooling.entity.User;
import com.carPooling.service.UserService;


@RestController
@RequestMapping("/")
@CrossOrigin()
public class RootController {
	@Autowired
	private UserService userService;
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO loginDTO)
	{
		String token=userService.verify(loginDTO.getUsername(),loginDTO.getPassword());
		String role=userService.getUserRole(loginDTO.getUsername());
		Map<String,String> res=new HashMap<>(); 
		res.put("token", token);
		res.put("username", loginDTO.getUsername());
		res.put("role", role);
		
		return ResponseEntity.ok(res);
		
	}
	@PostMapping("/register")
	public ResponseEntity<ResponseDTO> register(@RequestBody User user)
	{
		
		userService.register(user);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseDTO(HttpStatus.ACCEPTED,"Registeration SucessFull"));
	}
}
