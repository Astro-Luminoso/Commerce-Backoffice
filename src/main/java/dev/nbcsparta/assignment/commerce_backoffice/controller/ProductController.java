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
    ResponseEntity<CreateProductResponse> createProduct(
            @RequestBody CreateProductRequest request
    ) {
        CreateProductResponse response = productService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
        int page = Math.max(pageable.getPageNumber(), 0);
        int size = Math.min(Math.max(pageable.getPageSize(), 1), 100);
        pageable = PageRequest.of(page, size, pageable.getSort());
        GetListProductResponse response = productService.getAllProduct(name, category, status, pageable);

        return CommonResponse
                .success(HttpStatus.OK, "상품 목록 조회 성공", response)
                .toResponseEntity();
    }


    @GetMapping("/products/{productId}")
    ResponseEntity<GetProductResponse> getOneProduct(
            @PathVariable Long productId
    ) {
        GetProductResponse response = productService.getOne(productId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/products/{productId}")
    ResponseEntity<UpdateProductResponse> updateProduct(
            @PathVariable Long productId,
            @RequestBody UpdateProductRequest request
    ) {
        UpdateProductResponse response = productService.update(productId, request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/products/{productId}/status")
    ResponseEntity<Void> updateProductStatus(
            @PathVariable Long productId,
            @RequestBody UpdateProductStatusRequest request
    ) {
        productService.updateStatus(productId, request);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/products/{productId}")
    ResponseEntity<Void> deleteProduct(
            @PathVariable Long productId
    ) {
        productService.delete(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
