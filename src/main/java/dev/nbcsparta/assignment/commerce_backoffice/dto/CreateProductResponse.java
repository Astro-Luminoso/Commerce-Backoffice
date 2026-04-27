package dev.nbcsparta.assignment.commerce_backoffice.dto;


import dev.nbcsparta.assignment.commerce_backoffice.entity.Product;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.ProductStatus;
import java.time.LocalDateTime;

public record CreateProductResponse(
        Long id,
        String name,
        String category,
        int price,
        int quantity,
        ProductStatus status,
        LocalDateTime createdAt,
        Long managerId
) {
    public static CreateProductResponse from(Product product) {
        return new CreateProductResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getQuantity(),
                product.getStatus(),
                product.getCreatedAt(),
                product.getManager().getId()
        );
    }
}
