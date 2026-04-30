package dev.nbcsparta.assignment.commerce_backoffice.dto.manager;

import dev.nbcsparta.assignment.commerce_backoffice.enumerate.Role;
import jakarta.validation.constraints.*;

public record CreateManagerRequest (
        @NotBlank(message = "매니저 이름은 필수입니다.")
        String name,

        @Email(message = "이메일 양식에 맞지 않습니다.")
        String email,

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 8)
        String password,

        @NotBlank(message = "핸드폰 번호는 필수입니다.")
        @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "휴대폰 번호 양식에 맞지 않습니다.")
        String phoneNumber,

        @NotNull(message = "역할은 필수입니다.")
        Role role
){
}

