package dev.nbcsparta.assignment.commerce_backoffice.dto;


import jakarta.validation.constraints.Size;

public record UpdateMyPasswordRequest (
        String password,

        @Size(min = 8)
        String newPassword
){
}