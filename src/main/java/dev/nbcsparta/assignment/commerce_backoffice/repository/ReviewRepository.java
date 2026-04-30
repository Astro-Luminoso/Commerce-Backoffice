package dev.nbcsparta.assignment.commerce_backoffice.repository;

import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.charts.ReviewRatingCount;
import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.data.ReviewDashboard;
import dev.nbcsparta.assignment.commerce_backoffice.dto.ReviewFilter;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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
        AND ( :#{#filter.rating} = 0 OR r.rating = :#{#filter.rating} )
        """)
    Page<Review> findAllReview(
            @Param("filter") ReviewFilter filter,
            Pageable pageable
    );

    @Query("SELECT new dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.data.ReviewDashboard(" +
            "COUNT(r)," +
            "COALESCE(AVG(r.rating), 0))" +
            "FROM Review r")
    ReviewDashboard getStatistics();

    @Query("SELECT new dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.charts.ReviewRatingCount( " +
            "r.rating, COUNT(r)) " +
            "FROM Review r " +
            "GROUP BY r.rating " +
            "ORDER BY r.rating")
    List<ReviewRatingCount> getRatingCount();

    @Query("""
    SELECT new dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.data.ReviewDashboard(
        COUNT(r),
        COALESCE(AVG(r.rating), 0)
    )
    FROM Review r
    WHERE r.product.id = :productId
""")
    ReviewDashboard getProductStatistics(@Param("productId") Long productId);

    @Query("""
    SELECT new dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.charts.ReviewRatingCount(
        r.rating,
        COUNT(r)
    )
    FROM Review r
    WHERE r.product.id = :productId
    GROUP BY r.rating
    ORDER BY r.rating
""")
    List<ReviewRatingCount> getProductRatingCount(@Param("productId") Long productId);

    List<Review> findTop3ByProduct_IdOrderByCreatedAtDesc(Long productId);
}
