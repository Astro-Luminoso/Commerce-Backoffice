package dev.nbcsparta.assignment.commerce_backoffice.dto;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Review;

import java.time.LocalDateTime;

public record GetReviewResponse(
        Long id,
        Long orderId,
        String customerName,
        String productName,
        int rating,
        String content,
        LocalDateTime createdAt
) {
    public static GetReviewResponse from(Review review) {
        return new GetReviewResponse(
                review.getId(),
                review.getOrder().getId(),
                review.getCustomer().getName(),
                review.getProduct().getName(),
                review.getRating(),
                review.getContent(),
                review.getCreatedAt()
        );
    }
}
