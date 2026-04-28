package dev.nbcsparta.assignment.commerce_backoffice.repository;


import dev.nbcsparta.assignment.commerce_backoffice.dto.ProductFilter;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
        SELECT p FROM Product p
        WHERE (:#{#filter.name()} IS NULL OR p.name LIKE CONCAT('%', :#{#filter.name()}, '%'))
          AND (:#{#filter.category()} IS NULL OR p.category = :#{#filter.category()})
          AND (:#{#filter.status()} IS NULL OR p.status = :#{#filter.status()})
        """)
    Page<Product> findAll(
            @Param("filter") ProductFilter filter,
            Pageable pageable
    );
}
