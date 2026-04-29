package dev.nbcsparta.assignment.commerce_backoffice.dto;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Review;

import java.time.LocalDateTime;

public record GetRecentReviewResponse(
        String customerName,
        Integer rating,
        String content,
        LocalDateTime createdAt
) {
    public static GetRecentReviewResponse from(Review review) {
        return new GetRecentReviewResponse(
                review.getCustomerName(),
                review.getRating(),
                review.getContent(),
                review.getCreatedAt()
        );
    }
}
