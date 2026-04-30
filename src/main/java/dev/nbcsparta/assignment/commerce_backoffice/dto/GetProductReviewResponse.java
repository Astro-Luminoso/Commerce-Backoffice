package dev.nbcsparta.assignment.commerce_backoffice.dto;

import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.charts.ReviewRatingCount;
import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.data.ReviewStatistics;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Product;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Review;

import java.util.List;

/**
 *
 * @param productResponse       상품 정보 반환 DTO
 * @param averageRating         상품의 평점 평균
 * @param totalCount            상품의 리뷰 갯수
 * @param reviewRatingCounts    상품의 평점별 리뷰 갯수
 * @param recentReviews         상품의 최신 리뷰 3건을 담은 리스트
 */
public record GetProductReviewResponse(
        GetProductResponse productResponse,
        Double averageRating,
        Long totalCount,
        List<ReviewRatingCount> reviewRatingCounts,
        List<GetRecentReviewResponse> recentReviews
) {
    /**
     *
     * @param reviewStatistics   상품의 평점 평균, 리뷰 갯수를 담은 DTO
     */
    public static GetProductReviewResponse from(
            Product product,
            ReviewStatistics reviewStatistics,
            List<ReviewRatingCount> ratingCounts,
            List<Review> reviews
    ) {
        return new GetProductReviewResponse(
                GetProductResponse.from(product),
                reviewStatistics.averageRating(),
                reviewStatistics.totalReviews(),
                ratingCounts,
                reviews.stream().map(GetRecentReviewResponse::from).toList()
        );
    }
}
