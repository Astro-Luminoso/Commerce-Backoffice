package dev.nbcsparta.assignment.commerce_backoffice.dto.customer;

import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateCustomerStatusRequest(
        @NotNull(message = "상태 입력이 필수입니다.") AccountStatus status
) {
}
