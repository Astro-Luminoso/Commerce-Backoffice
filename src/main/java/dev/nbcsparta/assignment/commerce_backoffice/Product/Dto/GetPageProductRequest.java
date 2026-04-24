package dev.nbcsparta.assignment.commerce_backoffice.Product.Dto;

import dev.nbcsparta.assignment.commerce_backoffice.config.ProductStatus;

public record GetPageProductRequest(
        String name,
        int page,
        int size,
        String sortValue,
        String sortBy,
        String category,
        ProductStatus productStatus
) {
}
