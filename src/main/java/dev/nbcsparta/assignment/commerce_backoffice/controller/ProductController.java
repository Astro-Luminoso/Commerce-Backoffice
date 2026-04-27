package dev.nbcsparta.assignment.commerce_backoffice.controller;


import dev.nbcsparta.assignment.commerce_backoffice.dto.*;
import dev.nbcsparta.assignment.commerce_backoffice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/products")
    public ResponseEntity<GetPageProductResponse<GetProductResponse>> getPageProducts(
            GetPageProductRequest request
    ){
        GetPageProductResponse<GetProductResponse> page
                = productService.getPageProducts(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(page);
    }


    @GetMapping("/products/{productId}")
    ResponseEntity<GetProductResponse> getOneProduct(
            @PathVariable Long productId
    ){
        GetProductResponse response = productService.getOne(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
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
    ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.delete(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
