package dev.nbcsparta.assignment.commerce_backoffice.Product.Controller;


import dev.nbcsparta.assignment.commerce_backoffice.Product.Dto.CreateProductRequest;
import dev.nbcsparta.assignment.commerce_backoffice.Product.Dto.CreateProductResponse;
import dev.nbcsparta.assignment.commerce_backoffice.Product.Service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


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


}
