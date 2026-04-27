package dev.nbcsparta.assignment.commerce_backoffice.dto;

import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;
import jakarta.validation.constraints.NotBlank;

public record UpdateCustomerStatusRequest(@NotBlank AccountStatus status) {
}
