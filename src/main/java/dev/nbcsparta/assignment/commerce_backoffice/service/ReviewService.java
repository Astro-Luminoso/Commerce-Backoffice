package dev.nbcsparta.assignment.commerce_backoffice.service;

import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.charts.ReviewRatingCount;
import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.data.ReviewDashboard;
import dev.nbcsparta.assignment.commerce_backoffice.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public ReviewDashboard getStatistics() {
        return reviewRepository.getStatistics();
    }

    @Transactional
    public List<ReviewRatingCount> getRatingCount() {
        return reviewRepository.getRatingCount();
    }
}
