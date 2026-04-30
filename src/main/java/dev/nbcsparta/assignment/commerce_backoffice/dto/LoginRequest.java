package dev.nbcsparta.assignment.commerce_backoffice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest (
        @Email(message = "이메일 양식에 맞지 않습니다.")
        String email,

        @NotBlank(message = "비밀번호는 필수입니다.")
        String password
){
}