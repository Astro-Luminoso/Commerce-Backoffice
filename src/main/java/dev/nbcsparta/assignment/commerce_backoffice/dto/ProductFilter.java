package dev.nbcsparta.assignment.commerce_backoffice.dto;

import dev.nbcsparta.assignment.commerce_backoffice.enumerate.ProductStatus;

public record ProductFilter(
        String name,
        String category,
        ProductStatus status
) {
}
