package dev.nbcsparta.assignment.commerce_backoffice.service;


import dev.nbcsparta.assignment.commerce_backoffice.dto.*;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Manager;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Product;
import dev.nbcsparta.assignment.commerce_backoffice.exception.ManagerNotFoundException;
import dev.nbcsparta.assignment.commerce_backoffice.exception.ProductNotFoundException;
import dev.nbcsparta.assignment.commerce_backoffice.exception.SortFailException;
import dev.nbcsparta.assignment.commerce_backoffice.repository.ManagerAuthRepository;
import dev.nbcsparta.assignment.commerce_backoffice.repository.ProductRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ManagerAuthRepository managerRepository;
    public ProductService(ProductRepository productRepository, ManagerAuthRepository managerRepository) {
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


    /**
     * name             상품 이름
     * page
     * size
     * sortValue        생성일
     * sortBy           내림차순, 오름차순
     * category         상품 카테고리
     * ProductStatus    상품상태 ( 판매중, 품절, 단종 )
     *
     * @return GetPageProductResponse()
     */

    @Transactional(readOnly = true)
    public GetPageProductResponse<GetProductResponse> getPageProducts(GetPageProductRequest req) {

        int page = req.page() != null ? req.page() : 0;
        int size = req.size() != null ? req.size() : 10;

        String sortBy = (req.sortBy() == null || req.sortBy().isBlank()) ? "createdAt" : req.sortBy();
        String sortValue = (req.sortValue() == null || req.sortValue().isBlank()) ? "DESC" : req.sortValue();

        String searchName = (req.name() != null && req.name().isBlank()) ? null : req.name();
        String searchCategory = (req.category() != null && req.category().isBlank()) ? null : req.category();

        /**
         * 위의 코드들은 정렬기준이 지정되지 않았거나 빈칸으로 왔을 때의 기본값 설정입니다
         *
         */

        Sort sort;
        try{
            sort = Sort.by(Sort.Direction.fromString(sortValue), sortBy);
        }catch (Exception e) {
            throw new SortFailException("정렬 기준이 잘못되었습니다.");
        }
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<GetProductResponse> pageResult = productRepository.searchProducts(
                searchName,
                searchCategory,
                req.productStatus(),
                pageable
        );

        return GetPageProductResponse.from(pageResult);
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
}
