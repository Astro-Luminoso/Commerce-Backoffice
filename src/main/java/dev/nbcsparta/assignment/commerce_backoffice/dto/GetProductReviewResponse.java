package dev.nbcsparta.assignment.commerce_backoffice.dto;

import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.charts.ReviewRatingCount;
import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.data.ReviewDashboard;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Product;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Review;

import java.util.List;

public record GetProductReviewResponse(
        GetProductResponse productResponse,
        Double averageRating,
        Long totalCount,
        List<ReviewRatingCount> reviewRatingCounts,
        List<GetRecentReviewResponse> recentReviews
) {
    public static GetProductReviewResponse from(
            Product product,
            ReviewDashboard reviewDashboard,
            List<ReviewRatingCount> ratingCounts,
            List<Review> reviews ) {
        return new GetProductReviewResponse(
                GetProductResponse.from(product),
                reviewDashboard.averageRating(),
                reviewDashboard.totalReviews(),
                ratingCounts,
                reviews.stream().map(GetRecentReviewResponse::from).toList()
        );
    }
}
