package dev.nbcsparta.assignment.commerce_backoffice.repository;

import dev.nbcsparta.assignment.commerce_backoffice.dto.ReviewFilter;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("""
        SELECT r FROM Review r
        JOIN r.customer c
        JOIN r.product p
        WHERE (
            :#{#filter.keyword} IS NULL
            OR c.name LIKE CONCAT('%', :#{#filter.keyword}, '%')
            OR p.name LIKE CONCAT('%', :#{#filter.keyword}, '%')
            OR r.content LIKE CONCAT('%', :#{#filter.keyword}, '%')
        )
        AND ( :#{#filter.rating} IS NULL OR r.rating = :#{#filter.rating} )
        """)
    Page<Review> findAllReview(
            @Param("filter") ReviewFilter filter,
            Pageable pageable
    );
}
