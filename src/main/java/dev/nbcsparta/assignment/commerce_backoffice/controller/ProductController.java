package dev.nbcsparta.assignment.commerce_backoffice.controller;


import dev.nbcsparta.assignment.commerce_backoffice.dto.*;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.ProductStatus;
import dev.nbcsparta.assignment.commerce_backoffice.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    ResponseEntity<CommonResponse<CreateProductResponse>> createProduct(
            @RequestBody CreateProductRequest request
    ) {
        CreateProductResponse response = productService.create(request);

        return CommonResponse
                .success(HttpStatus.OK, "상품 등록 성공", response)
                .toResponseEntity();
    }

    /**
     *
     * @param name     상품 이름
     * @param category 상품 카테고리
     * @param status   상품 상태
     * @return <GetListProductResponse<GetPageProductResponse>>
     */
    @GetMapping("/products")
    public ResponseEntity<CommonResponse<GetListProductResponse>> getAllPage(
            @PageableDefault(page = 1, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) ProductStatus status
    ) {
        int page = Math.max(pageable.getPageNumber() - 1, 0);
        int size = Math.min(Math.max(pageable.getPageSize(), 1), 100);
        pageable = PageRequest.of(page, size, pageable.getSort());
        ProductFilter productFilter = new ProductFilter(name, category, status);
        GetListProductResponse response = productService.getAllProduct(pageable, productFilter);

        return CommonResponse
                .success(HttpStatus.OK, "상품 목록 조회 성공", response)
                .toResponseEntity();
    }


    @GetMapping("/products/{productId}")
    ResponseEntity<CommonResponse<GetProductResponse>> getOneProduct(
            @PathVariable Long productId
    ) {
        GetProductResponse response = productService.getOne(productId);

        return CommonResponse
                .success(HttpStatus.OK, "상품 상세 조회 성공", response)
                .toResponseEntity();
    }

    @PutMapping("/products/{productId}")
    ResponseEntity<CommonResponse<UpdateProductResponse>> updateProduct(
            @PathVariable Long productId,
            @RequestBody UpdateProductRequest request
    ) {
        UpdateProductResponse response = productService.update(productId, request);

        return CommonResponse
                .success(HttpStatus.OK, "상품 정보 수정 성공", response)
                .toResponseEntity();
    }

    @PatchMapping("/products/{productId}/status")
    ResponseEntity<CommonResponse<Void>> updateProductStatus(
            @PathVariable Long productId,
            @RequestBody UpdateProductStatusRequest request
    ) {
        productService.updateStatus(productId, request);

        return CommonResponse
                .success(HttpStatus.OK, "상품 상태 변경 완료")
                .toResponseEntity();
    }

    @DeleteMapping("/products/{productId}")
    ResponseEntity<CommonResponse<Void>> deleteProduct(
            @PathVariable Long productId
    ) {
        productService.delete(productId);

        return CommonResponse
                .success(HttpStatus.OK, "상품 삭제 성공")
                .toResponseEntity();
    }
}
