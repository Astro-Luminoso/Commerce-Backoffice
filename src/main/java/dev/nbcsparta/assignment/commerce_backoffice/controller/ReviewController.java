package dev.nbcsparta.assignment.commerce_backoffice.controller;

import dev.nbcsparta.assignment.commerce_backoffice.dto.*;
import dev.nbcsparta.assignment.commerce_backoffice.service.ReviewService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/reviews")
    ResponseEntity<CommonResponse<GetListReviewResponse>> getListReview(
            @PageableDefault(page = 1, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) int rating
    ) {
        ReviewFilter reviewFilter = new ReviewFilter(keyword, rating);
        GetListReviewResponse response = reviewService.getAllReview(pageable, reviewFilter);

        return CommonResponse
                .success(HttpStatus.OK, "리뷰 목록 조회 성공", response)
                .toResponseEntity();
    }

    @GetMapping("/reviews/{reviewId}")
    ResponseEntity<CommonResponse<GetDetailReviewResponse>> getDetailReview(
            @PathVariable Long reviewId
    ) {
        GetDetailReviewResponse response = reviewService.getOneReview(reviewId);

        return CommonResponse
                .success(HttpStatus.OK, "리뷰 상세 조회 성공", response)
                .toResponseEntity();
    }
}
