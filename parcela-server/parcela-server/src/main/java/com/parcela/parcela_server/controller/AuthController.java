	package com.parcela.parcela_server.controller;
	
	import com.parcela.parcela_server.dto.LoginRequest;
	import com.parcela.parcela_server.entity.Customer;
	import com.parcela.parcela_server.service.impl.AuthService;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.*;
	
	@RestController
	@RequestMapping("/api/auth")
	@CrossOrigin(origins = "*")
	public class AuthController {
	    @Autowired
	    private AuthService authService;
	
	    @PostMapping("/register")
	    public ResponseEntity<Customer> register(@RequestBody Customer customer) {
	        return ResponseEntity.ok(authService.register(customer));
	    }
	
	    @PostMapping("/login")
	    public ResponseEntity<Customer> login(@RequestBody LoginRequest loginRequest) {
	        return ResponseEntity.ok(authService.login(loginRequest));
	    }
	}