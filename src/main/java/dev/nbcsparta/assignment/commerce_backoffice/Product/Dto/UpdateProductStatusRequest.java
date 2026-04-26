package dev.nbcsparta.assignment.commerce_backoffice.Product.Dto;

import dev.nbcsparta.assignment.commerce_backoffice.config.ProductStatus;

public record UpdateProductStatusRequest(
        ProductStatus status
) {
}
