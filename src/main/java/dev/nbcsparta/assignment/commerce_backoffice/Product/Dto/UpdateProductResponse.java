package dev.nbcsparta.assignment.commerce_backoffice.Product.Dto;

public record UpdateProductResponse(
        String name,
        String category,
        Long price
) {
}
