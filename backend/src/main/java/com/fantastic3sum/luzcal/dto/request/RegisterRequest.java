
package com.fantastic3sum.luzcal.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

    @NotBlank
    @Size(min = 3, max = 50)
    String username,

    @NotBlank
    String firstName,

    @NotBlank
    String lastName,

    @NotBlank
    @Email
    String email,

    @NotBlank
    @Size(min = 8)
    String password

) {}
