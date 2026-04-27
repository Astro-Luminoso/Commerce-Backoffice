package dev.nbcsparta.assignment.commerce_backoffice.dto;

import dev.nbcsparta.assignment.commerce_backoffice.enumerate.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateProductRequest(

        @NotBlank
        String name,

        @NotBlank
        String category,

        @NotNull
        int price,

        @NotNull
        int quantity,

        @NotNull
        ProductStatus status,

        @NotNull
        Long managerId
) {
}
