package dev.nbcsparta.assignment.commerce_backoffice.dto.product;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Product;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.ProductStatus;

import java.time.LocalDateTime;

public record GetPageProductResponse(
        Long id,
        String name,
        String category,
        int price,
        int quantity,
        ProductStatus status,
        LocalDateTime createdAt,
        String managerName
) {

    public static GetPageProductResponse from(Product product) {
        return new GetPageProductResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getQuantity(),
                product.getStatus(),
                product.getCreatedAt(),
                product.getManager().getName()
        );
    }
}
