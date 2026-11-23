package com.fantastic3sum.luzcal.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest
(
    @Size(min = 3, max = 50)
    @Pattern(regexp = "\\S+", message = "Username cannot contain whitespace")
    String username,

    @Size(min = 1, max = 50)
    String firstName,

    @Size(min = 1, max = 50)
    String lastName
) {}
