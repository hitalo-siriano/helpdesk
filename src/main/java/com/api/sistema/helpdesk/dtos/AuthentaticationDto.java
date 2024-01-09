package com.api.sistema.helpdesk.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AuthentaticationDto(@NotNull @Size(max = 255) String login ,@NotNull @Size(max = 255) String password) {
    
}
