package dev.nbcsparta.assignment.commerce_backoffice.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record GetListProductResponse<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
    public static GetListProductResponse<GetPageProductResponse> from(Page<GetPageProductResponse> pageResult) {
        return new GetListProductResponse<>(
                pageResult.getContent(),
                pageResult.getNumber() + 1,
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages()
        );
    }
}
