package dev.nbcsparta.assignment.commerce_backoffice.dto;

import dev.nbcsparta.assignment.commerce_backoffice.enumerate.ProductStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record GetPageProductRequest(
        String name,

        @Min(0)
        Integer page,

        @Min(1)
        @Max(100)
        Integer size,

        String sortValue,

        String sortBy,

        String category,

        ProductStatus productStatus
) {
}
