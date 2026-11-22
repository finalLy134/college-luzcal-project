package com.fantastic3sum.luzcal.service;

import java.util.List;
import java.util.Locale;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import com.fantastic3sum.luzcal.dto.request.LoginRequest;
import com.fantastic3sum.luzcal.dto.request.RegisterRequest;
import com.fantastic3sum.luzcal.dto.response.UserResponse;
import com.fantastic3sum.luzcal.entity.User;
import com.fantastic3sum.luzcal.exception.UserAlreadyExistsException;
import com.fantastic3sum.luzcal.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityContextRepository securityContextRepository;
    private final SessionAuthenticationStrategy sessionAuthenticationStrategy;

    public void login(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        String identifier = loginRequest.identifier().trim().toLowerCase(Locale.ROOT);

        // Find the user in the database
        User user = userRepository.findByEmail(identifier)
            .or(() -> userRepository.findByUsername(identifier))
            .orElseThrow(() ->
                new BadCredentialsException("Invalid credentials")
            );

        // Verify the password
        if (user.getPasswordHash() == null ||
            !passwordEncoder.matches(
                loginRequest.password(),
                user.getPasswordHash()
            )) {
            throw new BadCredentialsException("Invalid credentials");
        }

        // Represent the authenticated user
        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(
            user.getId().toString(), null, List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        // Apply session security protections
        request.getSession(true);

        sessionAuthenticationStrategy.onAuthentication(authentication, request, response);

        // Create a SecurityContext for this request
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        // Save the context to the HTTP session
        securityContextRepository.saveContext(context, request, response);
    }

    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username()))
            throw new UserAlreadyExistsException("User with that username already exists");

        if (userRepository.existsByEmail(request.email()))
            throw new UserAlreadyExistsException("User with that email already exists");

        String passwordHash = passwordEncoder.encode(request.password());
        
        User newUser = new User();

        newUser.setUsername(request.username());
        newUser.setPasswordHash(passwordHash);
        newUser.setFirstName(request.firstName());
        newUser.setLastName(request.lastName());
        newUser.setEmail(request.email());

        userRepository.save(newUser);

        return UserResponse.toResponse(newUser);
    }

}
