package dev.nbcsparta.assignment.commerce_backoffice.dto;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Review;
import org.springframework.data.domain.Page;

import java.util.List;

public record GetListReviewResponse(
        List<GetReviewResponse> reviews,
        PageInfo pageInfo
) {
    public static GetListReviewResponse from(Page<Review> pageProduct) {
        List<GetReviewResponse> reviews = pageProduct.getContent().stream()
                .map(GetReviewResponse::from).toList();

        return new GetListReviewResponse(reviews, PageInfo.from(pageProduct));
    }
}
