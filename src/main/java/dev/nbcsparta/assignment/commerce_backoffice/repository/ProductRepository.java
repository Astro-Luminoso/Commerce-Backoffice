package dev.nbcsparta.assignment.commerce_backoffice.repository;


import dev.nbcsparta.assignment.commerce_backoffice.dto.ProductFilter;
import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.charts.ProductCategoryCount;
import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.data.ProductStatistics;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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

    @Query("SELECT new dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.data.ProductStatistics(" +
            "COUNT(p)," +
            "SUM(CASE WHEN p.quantity <= 5 THEN 1 ELSE 0 END)," +
            "SUM(CASE WHEN p.status = dev.nbcsparta.assignment.commerce_backoffice.enumerate.ProductStatus.SOLD_OUT THEN 1 ELSE 0 END))" +
            "FROM Product p")
    ProductStatistics getStatistics();

    @Query("SELECT new dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.charts.ProductCategoryCount(" +
            "p.category, COUNT(p)) " +
            "FROM Product p " +
            "GROUP BY p.category")
    List<ProductCategoryCount> getCategoryCount();
}
