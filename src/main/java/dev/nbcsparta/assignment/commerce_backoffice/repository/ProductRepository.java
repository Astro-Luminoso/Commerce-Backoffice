package dev.nbcsparta.assignment.commerce_backoffice.repository;


import dev.nbcsparta.assignment.commerce_backoffice.dto.GetPageProductResponse;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Product;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
SELECT p
FROM Product p
LEFT JOIN FETCH p.manager
WHERE (:name IS NULL OR p.name LIKE CONCAT('%', :name, '%'))
  AND (:category IS NULL OR p.category = :category)
  AND (:status IS NULL OR p.status = :status)
""")
    Page<Product> findAll(
            @Param("name") String name,
            @Param("category") String category,
            @Param("status") ProductStatus status,
            Pageable pageable
    );
}
