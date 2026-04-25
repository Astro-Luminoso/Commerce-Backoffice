package dev.nbcsparta.assignment.commerce_backoffice.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateCustomerStatusRequest(@NotBlank String status) {
}
