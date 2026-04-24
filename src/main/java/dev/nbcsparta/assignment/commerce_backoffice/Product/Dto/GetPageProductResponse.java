package dev.nbcsparta.assignment.commerce_backoffice.Product.Dto;

import java.util.List;

public class GetPageProductResponse<T> {

    private final List<T> content;
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;


    public GetPageProductResponse(
            List<T> content,
            int page,
            int size,
            long totalElements,
            int totalPages) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }


}
