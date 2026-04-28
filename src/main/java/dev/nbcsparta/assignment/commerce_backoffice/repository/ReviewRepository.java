package dev.nbcsparta.assignment.commerce_backoffice.repository;

import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.charts.ReviewRatingCount;
import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.data.ReviewDashboard;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

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
}
