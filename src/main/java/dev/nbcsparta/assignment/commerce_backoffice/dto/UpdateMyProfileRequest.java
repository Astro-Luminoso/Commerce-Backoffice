package dev.nbcsparta.assignment.commerce_backoffice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateMyProfileRequest (
        @NotBlank(message = "이름은 필수입니다.")
        String name,

        @Email(message = "이메일 양식에 맞지 않습니다.")
        String email,

        @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "휴대폰 번호 양식에 맞지 않습니다.")
        String phoneNumber
){
}
