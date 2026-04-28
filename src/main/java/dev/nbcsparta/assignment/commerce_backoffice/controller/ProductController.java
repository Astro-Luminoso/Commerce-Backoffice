package dev.nbcsparta.assignment.commerce_backoffice.controller;


import dev.nbcsparta.assignment.commerce_backoffice.dto.*;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Product;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.ProductStatus;
import dev.nbcsparta.assignment.commerce_backoffice.service.ProductService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
     * @param page
     * @param size
     * @param sortName 정렬 항목 이름  ex) createdAt
     * @param sortBy   정렬 기준, DESC
     * @param category 상품 카테고리
     * @param status   상품 상태
     * @return <GetListProductResponse<GetPageProductResponse>>
     */
    @GetMapping("/products")
    public ResponseEntity<GetListProductResponse<GetPageProductResponse>> getAllPage(
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "createdAt") String sortName,
            @RequestParam(required = false, defaultValue = "DESC") String sortBy,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) ProductStatus status
    ) {
        GetListProductResponse<GetPageProductResponse> pageable = productService.getAllProduct(
                name, page, size, sortName, sortBy, category, status
        );

        return ResponseEntity.status(HttpStatus.OK).body(pageable);
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
