package dev.nbcsparta.assignment.commerce_backoffice.dto;

import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateCustomerStatusRequest(@NotNull AccountStatus status) {
}
