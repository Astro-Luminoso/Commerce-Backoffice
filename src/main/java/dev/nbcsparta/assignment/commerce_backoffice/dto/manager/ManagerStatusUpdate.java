package dev.nbcsparta.assignment.commerce_backoffice.dto.manager;

import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;
import jakarta.validation.constraints.NotNull;

public record ManagerStatusUpdate(
        @NotNull(message = "상태는 필수입니다.")
        AccountStatus status,

        String reason
) {
}
