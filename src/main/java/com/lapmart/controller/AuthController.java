package com.lapmart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lapmart.dto.AuthResponse;
import com.lapmart.dto.LoginRequest;
import com.lapmart.dto.RegisterRequest;
import com.lapmart.service.impl.AuthServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthServiceImpl authService;

	public AuthController(AuthServiceImpl authService) {
		this.authService = authService;
	}
	
	// POST /api/auth/register
    // @Valid triggers the validation annotations in RegisterRequest
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register( @Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
