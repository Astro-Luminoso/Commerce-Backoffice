package dev.nbcsparta.assignment.commerce_backoffice.dto;

import java.time.LocalDateTime;

public record CreateReviewResponse(
        String productName,
        String customerName,
        String customerEmail,
        LocalDateTime createdAt,
        int rating,
        String content
) {
}
