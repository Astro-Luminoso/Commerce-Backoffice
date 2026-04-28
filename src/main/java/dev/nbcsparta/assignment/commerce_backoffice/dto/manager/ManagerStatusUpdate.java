package dev.nbcsparta.assignment.commerce_backoffice.dto.manager;

import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;
import jakarta.validation.constraints.NotNull;

public record ManagerStatusUpdate(
        @NotNull
        AccountStatus status,

        String reason
) {
}
