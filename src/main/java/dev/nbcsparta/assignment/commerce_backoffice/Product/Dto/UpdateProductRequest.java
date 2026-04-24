package dev.nbcsparta.assignment.commerce_backoffice.Product.Dto;

public record UpdateProductRequest(
        String name,
        String category,
        Long price
) {
}
