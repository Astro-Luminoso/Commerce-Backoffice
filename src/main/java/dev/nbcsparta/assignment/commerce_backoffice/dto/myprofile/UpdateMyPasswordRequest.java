package dev.nbcsparta.assignment.commerce_backoffice.dto.myprofile;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateMyPasswordRequest (
        @NotBlank(message = "현재 비밀번호는 필수입니다.")
        String password,

        @NotBlank(message = "새 비밀번호는 필수입니다.")
        @Size(min = 8)
        String newPassword
){
}