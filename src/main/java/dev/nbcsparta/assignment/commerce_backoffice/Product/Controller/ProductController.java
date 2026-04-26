package dev.nbcsparta.assignment.commerce_backoffice.Product.Controller;


import dev.nbcsparta.assignment.commerce_backoffice.Product.Dto.*;
import dev.nbcsparta.assignment.commerce_backoffice.Product.Entity.Product;
import dev.nbcsparta.assignment.commerce_backoffice.Product.Service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(request));
    }

    @GetMapping("/products")
    public ResponseEntity<GetPageProductResponse<GetProductResponse>> getPageProducts(
            GetPageProductRequest request
    ){
        return ResponseEntity.ok(productService.getPageProducts(request));
    }


    @GetMapping("/products/{productId}")
    ResponseEntity<GetProductResponse> getOneProduct(
            @PathVariable Long productId
    ){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getOne(productId));
    }

    @PutMapping("/products/{productId}")
    ResponseEntity<UpdateProductResponse> updateProduct(
            @PathVariable Long productId,
            @RequestBody UpdateProductRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.update(productId, request));
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
