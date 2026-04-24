package dev.nbcsparta.assignment.commerce_backoffice.Product.Dto;

import dev.nbcsparta.assignment.commerce_backoffice.config.ProductStatus;

import java.time.LocalDateTime;

public record GetProductResponse(
        Long id,
        String name,
        String category,
        Long price,
        Long quantity,
        ProductStatus status,
        LocalDateTime createdAt,
        Long managerId,
        String managerEmail
) {
}
