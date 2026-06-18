package com.lapmart.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lapmart.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
	// OncePerRequestFilter → guarantees this filter runs exactly once per request

	private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    
	public JwtFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// 1. Read the Authorization header
        // Expected format: "Bearer eyJhbGciOiJIUzI1NiJ9..."
        String authHeader = request.getHeader("Authorization");
        
        // 2. If no token present, skip this filter (public routes)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // pass to next filter
            return;
        }
        
        // 3. Extract the token (remove "Bearer " prefix)
        String token = authHeader.substring(7);
        
        // 4. Validate token
        if (!jwtUtil.isTokenValid(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // 5. Extract email from token
        String email = jwtUtil.extractEmail(token);
        
        // 6. Only authenticate if not already authenticated
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 7. Load user from DB
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // 8. Create authentication object
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,                        // no credentials needed post-validation
                            userDetails.getAuthorities() // roles
                    );

            authToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
            );

            // 9. Set authentication in Spring Security context
            // After this, Spring knows who the current user is
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // 10. Pass request to next filter / controller
        filterChain.doFilter(request, response);
	}
}
