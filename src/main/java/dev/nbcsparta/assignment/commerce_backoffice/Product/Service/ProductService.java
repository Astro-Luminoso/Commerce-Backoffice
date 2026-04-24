package dev.nbcsparta.assignment.commerce_backoffice.Product.Service;

import dev.nbcsparta.assignment.commerce_backoffice.Manager.Entity.Manager;
import dev.nbcsparta.assignment.commerce_backoffice.Manager.Repository.ManagerRepository;
import dev.nbcsparta.assignment.commerce_backoffice.Product.Dto.*;
import dev.nbcsparta.assignment.commerce_backoffice.Product.Entity.Product;
import dev.nbcsparta.assignment.commerce_backoffice.Product.Repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        int page = (req.page() <= 0) ? 1 : req.page() - 1;
        int size = (req.size() <= 0) ? 10 : req.size();

        Pageable pageable = PageRequest.of(page, size);

        Page<GetProductResponse> pageResult = productRepository.searchProducts(
                req.name(),
                req.category(),
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



    @Transactional(readOnly = true)
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
            throw new IllegalStateException("검색된 스케쥴이 없습니다.");
        }
        productRepository.deleteById(id);
    }
}
