package dev.nbcsparta.assignment.commerce_backoffice.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateMyPasswordRequest (
        String password,

        @NotBlank
        @Size(min = 8)
        String newPassword
){
}