package dev.nbcsparta.assignment.commerce_backoffice.service;

import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.charts.ReviewRatingCount;
import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.data.ReviewDashboard;
import dev.nbcsparta.assignment.commerce_backoffice.dto.*;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Review;
import dev.nbcsparta.assignment.commerce_backoffice.exception.ReviewNotFoundException;
import dev.nbcsparta.assignment.commerce_backoffice.repository.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
    }


    @Transactional(readOnly = true)
    public GetListReviewResponse getAllReview(Pageable pageable, ReviewFilter reviewFilter) {
        Page<Review> reviewPage = reviewRepository.findAllReview(reviewFilter, pageable);

        return GetListReviewResponse.from(reviewPage);
    }

    @Transactional(readOnly = true)
    public GetDetailReviewResponse getOneReview(Long reviewId) {
        Review review = getReviewById(reviewId);

        return GetDetailReviewResponse.from(review);
    }

    public ReviewDashboard getStatistics() {
        return reviewRepository.getStatistics();
    }

    public List<ReviewRatingCount> getRatingCount() {
        return reviewRepository.getRatingCount();
    }
}
