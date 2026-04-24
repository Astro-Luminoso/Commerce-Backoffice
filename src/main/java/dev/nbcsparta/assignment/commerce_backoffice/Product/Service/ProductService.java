package dev.nbcsparta.assignment.commerce_backoffice.Product.Service;

import dev.nbcsparta.assignment.commerce_backoffice.Manager.Entity.Manager;
import dev.nbcsparta.assignment.commerce_backoffice.Manager.Repository.ManagerRepository;
import dev.nbcsparta.assignment.commerce_backoffice.Product.Dto.CreateProductRequest;
import dev.nbcsparta.assignment.commerce_backoffice.Product.Dto.CreateProductResponse;
import dev.nbcsparta.assignment.commerce_backoffice.Product.Entity.Product;
import dev.nbcsparta.assignment.commerce_backoffice.Product.Repository.ProductRepository;
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
                Product.of(request, manager)
        );

        return new CreateProductResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getQuantity(),
                product.getStatus(),
                product.getManager().getId()
        );
    }
}
