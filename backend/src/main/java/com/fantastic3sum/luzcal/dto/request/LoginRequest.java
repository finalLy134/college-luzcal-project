
package com.fantastic3sum.luzcal.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

    @NotBlank
    String identifier,

    @NotBlank
    String password

) {}
