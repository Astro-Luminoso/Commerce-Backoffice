package dev.nbcsparta.assignment.commerce_backoffice.dto.review;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Review;

import java.time.LocalDateTime;

public record GetDetailReviewResponse(
        String productName,
        String customerName,
        String customerEmail,
        LocalDateTime createdAt,
        int rating,
        String content
) {
    public static GetDetailReviewResponse from(Review review) {
        return new GetDetailReviewResponse(
                review.getProductName(),
                review.getCustomerName(),
                review.getCustomerEmail(),
                review.getCreatedAt(),
                review.getRating(),
                review.getContent()
        );
    }
}
