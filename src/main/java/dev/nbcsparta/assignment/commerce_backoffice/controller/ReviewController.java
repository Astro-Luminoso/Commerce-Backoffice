package dev.nbcsparta.assignment.commerce_backoffice.controller;

import dev.nbcsparta.assignment.commerce_backoffice.dto.*;
import dev.nbcsparta.assignment.commerce_backoffice.dto.review.GetDetailReviewResponse;
import dev.nbcsparta.assignment.commerce_backoffice.dto.review.GetListReviewResponse;
import dev.nbcsparta.assignment.commerce_backoffice.dto.review.ReviewFilter;
import dev.nbcsparta.assignment.commerce_backoffice.service.ReviewService;
import org.springframework.data.domain.PageRequest;
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
            @PageableDefault(page = 1, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int rating
    ) {
        int page = Math.max(pageable.getPageNumber() - 1, 0);
        Pageable currentPageable = PageRequest.of(page, pageable.getPageSize(), pageable.getSort());
        ReviewFilter reviewFilter = new ReviewFilter(keyword, rating);
        GetListReviewResponse response = reviewService.getAllReview(currentPageable, reviewFilter);

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

    @DeleteMapping("/reviews/{reviewId}")
    ResponseEntity<CommonResponse<Void>> deleteReview(
            @PathVariable Long reviewId
    ){
        reviewService.delete(reviewId);

        return CommonResponse
                .success(HttpStatus.NO_CONTENT, "리뷰가 삭제되었습니다")
                .toResponseEntity();
    }
}
