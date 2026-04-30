package dev.nbcsparta.assignment.commerce_backoffice.dto.product;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Product;

public record UpdateProductResponse(
        String name,
        String category,
        int price
) {
    public static UpdateProductResponse from(Product product) {
        return new UpdateProductResponse(
                product.getName(),
                product.getCategory(),
                product.getPrice()
        );
    }
}
