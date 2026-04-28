package dev.nbcsparta.assignment.commerce_backoffice.service;

import dev.nbcsparta.assignment.commerce_backoffice.dto.*;
import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.charts.ProductCategoryCount;
import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.data.ProductDashboard;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Manager;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Product;
import dev.nbcsparta.assignment.commerce_backoffice.exception.ManagerNotFoundException;
import dev.nbcsparta.assignment.commerce_backoffice.exception.ProductNotFoundException;
import dev.nbcsparta.assignment.commerce_backoffice.repository.ManagerRepository;
import dev.nbcsparta.assignment.commerce_backoffice.repository.ProductRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ManagerRepository managerRepository;

    public ProductService(ProductRepository productRepository, ManagerRepository managerRepository) {
        this.productRepository = productRepository;
        this.managerRepository = managerRepository;
    }

    @Transactional
    public CreateProductResponse create(CreateProductRequest request) {
        Manager manager = managerRepository.findById(request.managerId()).orElseThrow(
                ManagerNotFoundException::new
        );

        Product product = productRepository.save(
                new Product(
                        request.name(),
                        request.category(),
                        request.price(),
                        request.quantity(),
                        request.status(),
                        manager
                )
        );

        return CreateProductResponse.from(product);
    }



    @Transactional(readOnly = true)
    public GetListProductResponse getAllProduct(Pageable pageable, ProductFilter productFilter) {
        Page<Product> productPage = productRepository.findAll(productFilter, pageable);

        return GetListProductResponse.from(productPage);
    }


    @Transactional()
    public GetProductResponse getOne(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                ProductNotFoundException::new
        );

        return GetProductResponse.from(product);
    }


    /**
     *
     * @param id        상품 아이디
     * @param request   name 상품이름,
     *                  Category 상품 카테고리,
     *                  Price 가격
     *
     * @return UpdateProductResponse
     */
    @Transactional
    public UpdateProductResponse update(Long id, UpdateProductRequest request) {
        Product product = productRepository.findById(id).orElseThrow(
                ProductNotFoundException::new
        );
        product.update(request.name(), request.category(), request.price());

        return UpdateProductResponse.from(product);
    }

    /**
     *
     * @param id        상품 아이디
     * @param request   ProductStatus 상품 상태
     */
    @Transactional
    public void updateStatus(Long id, UpdateProductStatusRequest request) {
        Product product = productRepository.findById(id).orElseThrow(
                ProductNotFoundException::new
        );
        product.setStatus(request.status());
    }


    @Transactional
    public void delete(Long id) {
        boolean existence = productRepository.existsById(id);
        if(!existence) {
            throw new ProductNotFoundException();
        }
        productRepository.deleteById(id);
    }

    @Transactional
    public ProductDashboard getStatistics() {
        return productRepository.getStatistics();
    }

    @Transactional
    public List<ProductCategoryCount> getCategoryCount() {
        return productRepository.getCategoryCount();
    }
}
