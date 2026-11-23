package com.fantastic3sum.luzcal.service;

import java.util.Locale;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fantastic3sum.luzcal.dto.request.UserUpdateRequest;
import com.fantastic3sum.luzcal.dto.response.UserResponse;
import com.fantastic3sum.luzcal.entity.User;
import com.fantastic3sum.luzcal.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class UserService {
    
    private final UserRepository userRepository;

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder
            .getContext()
            .getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Not authenticated"
            );
        }

        Long userId = Long.valueOf(authentication.getName());

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "User not found"
            ));

        return user;
    }

    public boolean isUsernameTaken(String username) {
        return userRepository.existsByUsername(username.trim().toLowerCase((Locale.ROOT)));
    }

    public UserResponse getCurrentUser() {
        return UserResponse.toResponse(getAuthenticatedUser());
    }
    
    public UserResponse updateCurrentUser(UserUpdateRequest request) {
        User user = getAuthenticatedUser();

        if (isUsernameTaken(request.username())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Username already taken"
            );
        }

        Optional.ofNullable(request.username())
            .ifPresent(user::setUsername);

        Optional.ofNullable(request.firstName())
            .ifPresent(user::setFirstName);

        Optional.ofNullable(request.lastName())
            .ifPresent(user::setLastName);

        userRepository.save(user);

        return UserResponse.toResponse(user);
    }

}
