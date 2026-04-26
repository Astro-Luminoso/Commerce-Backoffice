package dev.nbcsparta.assignment.commerce_backoffice.dto;

import dev.nbcsparta.assignment.commerce_backoffice.enumerate.ProductStatus;

public record CreateProductRequest(
        String name,
        String category,
        Long price,
        Long quantity,
        ProductStatus status,
        Long managerId
) {
}
