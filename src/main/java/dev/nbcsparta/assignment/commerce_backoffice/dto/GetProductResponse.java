package dev.nbcsparta.assignment.commerce_backoffice.dto;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Product;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.ProductStatus;

import java.time.LocalDateTime;

public record GetProductResponse(

        Long id,
        String name,
        String category,
        Long price,
        Long quantity,
        ProductStatus status,
        LocalDateTime createdAt,
        Long managerId,
        String managerEmail
) {
    public static GetProductResponse from(Product product){
        return new GetProductResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getQuantity(),
                product.getStatus(),
                product.getCreatedAt(),
                product.getManager().getId(),
                product.getManager().getEmail()
        );
    }
}
