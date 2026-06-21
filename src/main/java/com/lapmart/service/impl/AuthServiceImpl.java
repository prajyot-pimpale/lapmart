package com.lapmart.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lapmart.dto.AuthResponse;
import com.lapmart.dto.LoginRequest;
import com.lapmart.dto.RegisterRequest;
import com.lapmart.entity.Cart;
import com.lapmart.entity.User;
import com.lapmart.repository.CartRepository;
import com.lapmart.repository.UserRepository;
import com.lapmart.util.JwtUtil;

@Service
public class AuthServiceImpl {
	
	private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    
    
	public AuthServiceImpl(UserRepository userRepository, CartRepository cartRepository,
			PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
		this.userRepository = userRepository;
		this.cartRepository = cartRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
		this.authenticationManager = authenticationManager;
	}
    
	// ─── Register ─────────────────────────────────────────────────
    public AuthResponse register(RegisterRequest request) {

        // 1. Check duplicate email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered!");
        }

        // 2. Build user entity with hashed password
        User user = new User();
    	user.setName(request.getName());
    	user.setEmail(request.getEmail());
    	user.setPassword(passwordEncoder.encode(request.getPassword()));
    	user.setRole(User.Role.USER);
       
        // 3. Save user to DB
        User savedUser = userRepository.save(user);

        // 4. Create an empty cart for this user automatically
        Cart cart = new Cart();
        cart.setUser(savedUser);                
        cartRepository.save(cart);

        // 5. Generate JWT token
        String token = jwtUtil.generateToken(savedUser.getEmail(), savedUser.getRole().name());

        return new AuthResponse(token, savedUser.getRole().name(), "Registration successful!");
    }

    // ─── Login ────────────────────────────────────────────────────
    public AuthResponse login(LoginRequest request) {

        // 1. Authenticate — Spring Security checks email + password
        // This internally calls CustomUserDetailsService.loadUserByUsername()
        // and compares BCrypt hashes
        // Throws BadCredentialsException if wrong
        authenticationManager.authenticate(
        		new UsernamePasswordAuthenticationToken(
        				request.getEmail(), 
        				request.getPassword())
        );

        // 2. If authentication passed, load user to get role
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. Generate JWT token
        String token = jwtUtil.generateToken(user.getEmail(),
                                             user.getRole().name());

        return new AuthResponse(token, user.getRole().name(), "Login successful!");
    }

}
