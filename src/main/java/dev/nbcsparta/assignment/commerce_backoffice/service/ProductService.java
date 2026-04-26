package dev.nbcsparta.assignment.commerce_backoffice.service;


import dev.nbcsparta.assignment.commerce_backoffice.dto.*;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Manager;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Product;
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
                () -> new IllegalStateException("해당 매니저가 없습니다.")
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

        return new CreateProductResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getQuantity(),
                product.getStatus(),
                product.getCreatedAt(),
                product.getManager().getId()
        );
    }


    @Transactional(readOnly = true)
    public GetPageProductResponse<GetProductResponse> getPageProducts(GetPageProductRequest req) {

        int page = (req.page() <= 0) ? 0 : req.page() - 1;
        int size = (req.size() <= 0) ? 10 : req.size();

        String sortBy = (req.sortBy() == null || req.sortBy().isBlank()) ? "createdAt" : req.sortBy();
        String sortDir = (req.sortValue() == null || req.sortValue().isBlank()) ? "DESC" : req.sortValue();

        String searchName = (req.name() != null && req.name().isBlank()) ? null : req.name();
        String searchCategory = (req.category() != null && req.category().isBlank()) ? null : req.category();

        /**
         * 위의 코드들은 정렬기준이 지정되지 않았거나 빈칸으로 왔을 때의 기본값 설정입니다
         *
         */

        Sort sort;
        try{
            sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
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

        return new GetPageProductResponse<>(
                pageResult.getContent(),
                pageResult.getNumber() + 1,
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages()
        );
    }

    @Transactional()
    public GetProductResponse getOne(Long id) {

        Product product = productRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("해당하는 상품이 없습니다.")
        );

        return new GetProductResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getQuantity(),
                product.getStatus(),
                product.getCreatedAt(),
                product.getManager().getId(),
                product.getManager().getEmail()
        );
    }

    @Transactional
    public UpdateProductResponse update(Long id, UpdateProductRequest request) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("해당 상품이 없습니다.")
        );

        product.update(request.name(), request.category(), request.price());
        return new UpdateProductResponse(
                product.getName(),
                product.getCategory(),
                product.getPrice()
        );

    }

    @Transactional
    public void updateStatus(Long id, UpdateProductStatusRequest request) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("해당 상품이 없습니다.")
        );

        product.setStatus(request.status());
    }

    @Transactional
    public void delete(Long id) {
        boolean existence = productRepository.existsById(id);
        if(!existence) {
            throw new IllegalStateException("검색된 상품이 없습니다.");
        }
        productRepository.deleteById(id);
    }
}
