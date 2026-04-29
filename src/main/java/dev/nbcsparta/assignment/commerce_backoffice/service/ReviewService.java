package dev.nbcsparta.assignment.commerce_backoffice.service;

import dev.nbcsparta.assignment.commerce_backoffice.dto.*;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Review;
import dev.nbcsparta.assignment.commerce_backoffice.repository.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }


    @Transactional(readOnly = true)
    public GetListReviewResponse getAllReview(Pageable pageable, ReviewFilter reviewFilter) {
        Page<Review> reviewPage = reviewRepository.findAllReview(reviewFilter, pageable);

        return GetListReviewResponse.from(reviewPage);
    }
}
