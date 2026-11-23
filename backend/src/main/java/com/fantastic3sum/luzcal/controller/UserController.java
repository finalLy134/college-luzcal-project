package com.fantastic3sum.luzcal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fantastic3sum.luzcal.dto.request.UserUpdateRequest;
import com.fantastic3sum.luzcal.dto.response.UserResponse;
import com.fantastic3sum.luzcal.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/api/users")
@RequiredArgsConstructor 
public class UserController {
    
    private final UserService userService;

    @GetMapping("/me")
    public UserResponse getCurrentUser() {
        return userService.getCurrentUser();
    }

    @PatchMapping("/me")
    public UserResponse updateCurrentUser(@Valid @RequestBody UserUpdateRequest request) {
        return userService.updateCurrentUser(request);
    }

    @GetMapping("/availability")
    public boolean isUsernameAvailable(@RequestParam @NotBlank String username) {
        return userService.isUsernameTaken(username);
    }

}
