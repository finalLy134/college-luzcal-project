package com.fantastic3sum.luzcal.dto.response;

import java.time.Instant;

import com.fantastic3sum.luzcal.entity.User;

public record UserResponse(
    Long id,
    String username,
    String firstName,
    String lastName,
    String email,
    Instant createdAt
) {
    public static UserResponse toResponse(User user) {
        return new UserResponse(
            user.getId(), 
            user.getUsername(), 
            user.getFirstName(), 
            user.getLastName(), 
            user.getEmail(), 
            user.getCreatedAt()
        );
    }
}
