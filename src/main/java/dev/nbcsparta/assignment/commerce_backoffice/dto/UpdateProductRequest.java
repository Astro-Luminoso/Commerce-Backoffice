package dev.nbcsparta.assignment.commerce_backoffice.dto;

public record UpdateProductRequest(

        String name,
        String category,
        int price

) {
}
