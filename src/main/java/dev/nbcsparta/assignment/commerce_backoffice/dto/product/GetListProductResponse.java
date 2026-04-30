package dev.nbcsparta.assignment.commerce_backoffice.dto.product;

import dev.nbcsparta.assignment.commerce_backoffice.dto.PageInfo;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public record GetListProductResponse (
        List<GetPageProductResponse> products,
        PageInfo pageInfo
) {
    public static GetListProductResponse from(Page<Product> pageProduct) {
        List<GetPageProductResponse> products = pageProduct.getContent().stream()
                .map(GetPageProductResponse::from).toList();

        return new GetListProductResponse(products, PageInfo.from(pageProduct));
    }
}
