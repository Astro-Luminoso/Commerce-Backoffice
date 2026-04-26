package dev.nbcsparta.assignment.commerce_backoffice.dto;


import dev.nbcsparta.assignment.commerce_backoffice.enumerate.ProductStatus;
import java.time.LocalDateTime;

public record CreateProductResponse(
        Long id,
        String name,
        String category,
        Long price,
        Long quantity,
        ProductStatus status,
        LocalDateTime createdAt,
        Long managerId
) {

}
