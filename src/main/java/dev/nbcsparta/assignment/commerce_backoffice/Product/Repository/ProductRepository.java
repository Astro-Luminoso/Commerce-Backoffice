package dev.nbcsparta.assignment.commerce_backoffice.Product.Repository;

import dev.nbcsparta.assignment.commerce_backoffice.Product.Dto.GetProductResponse;
import dev.nbcsparta.assignment.commerce_backoffice.Product.Entity.Product;
import dev.nbcsparta.assignment.commerce_backoffice.config.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {


    @Query("""
    SELECT p FROM Product p
    WHERE (:name IS NULL OR p.name LIKE %:name%)
      AND (:category IS NULL OR p.category = :category)
      AND (:status IS NULL OR p.status = :status)
""")
    Page<GetProductResponse> searchProducts(
            @Param("name") String name,
            @Param("category") String category,
            @Param("status") ProductStatus status,
            Pageable pageable
    );


}
