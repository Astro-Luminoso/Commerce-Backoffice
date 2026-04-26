package dev.nbcsparta.assignment.commerce_backoffice.dto;

public record UpdateProductResponse(
        String name,
        String category,
        Long price
) {
}
