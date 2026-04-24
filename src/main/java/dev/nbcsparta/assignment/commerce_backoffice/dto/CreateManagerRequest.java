package dev.nbcsparta.assignment.commerce_backoffice.dto;

import dev.nbcsparta.assignment.commerce_backoffice.enumerate.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateManagerRequest (
        @NotBlank
        String name,

        @Email
        String email,

        @NotBlank
        @Size(min = 8)
        String password,

        @NotBlank
        String phoneNumber,

        @NotNull
        Role role
){
}

