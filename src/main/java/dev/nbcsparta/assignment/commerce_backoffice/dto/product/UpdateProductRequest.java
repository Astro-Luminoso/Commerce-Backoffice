package dev.nbcsparta.assignment.commerce_backoffice.dto.product;

public record UpdateProductRequest(

        String name,
        String category,
        int price

) {
}
