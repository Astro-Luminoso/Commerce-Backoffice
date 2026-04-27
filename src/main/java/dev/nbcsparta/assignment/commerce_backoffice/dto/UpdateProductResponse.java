package dev.nbcsparta.assignment.commerce_backoffice.dto;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Product;

public record UpdateProductResponse(
        String name,
        String category,
        Long price
) {
    public static UpdateProductResponse from(Product product) {
        return new UpdateProductResponse(
                product.getName(),
                product.getCategory(),
                product.getPrice()
        );
    }
}
