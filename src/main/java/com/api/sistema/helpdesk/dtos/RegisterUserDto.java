package com.api.sistema.helpdesk.dtos;

import com.api.sistema.helpdesk.enums.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterUserDto(@NotNull @Size(max = 255) String name , @NotNull @Size(max = 255) String Job ,@NotNull @Size(max = 255) @Email String email ,@NotNull @Size(max = 255) String department ,@NotNull @Size(max = 255) String login ,@NotNull @Size(max = 255) String password ,@NotNull  UserRole role) {
    
}
