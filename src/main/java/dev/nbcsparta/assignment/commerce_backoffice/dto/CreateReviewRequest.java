package dev.nbcsparta.assignment.commerce_backoffice.dto;

public record CreateReviewRequest(
        Long orderId,
        Long productId,
        Long customerId,
        int rating,
        String content
) {

}
