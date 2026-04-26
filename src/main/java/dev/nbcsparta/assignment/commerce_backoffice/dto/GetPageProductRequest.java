package dev.nbcsparta.assignment.commerce_backoffice.dto;

import dev.nbcsparta.assignment.commerce_backoffice.enumerate.ProductStatus;

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
