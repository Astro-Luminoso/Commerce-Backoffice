package dev.nbcsparta.assignment.commerce_backoffice.Product.Dto;

import dev.nbcsparta.assignment.commerce_backoffice.config.ProductStatus;

public record CreateProductRequest(
        String name,
        String category,
        Long price,
        Long quantity,
        ProductStatus status,
        Long managerId
) {
}
