package dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard;

/**
 * @param totalUsers 전체 관리자 수
 * @param activeUsers 활성 관리자 수
 * @param totalCustomers 전체 고객 수
 * @param activeCustomers 활성 고객 수
 * @param totalProducts 전체 상품 수
 * @param lowStockProducts 재고 부족 상품 수 (5개 이하)
 * @param totalOrders 전체 주문 수
 * @param todayOrders 오늘 주문 수
 * @param totalReviews 전체 리뷰 수
 * @param averageRating 리뷰 평균 평점
 */
public record DashboardSummary(
        long totalUsers,
        long activeUsers,
        long totalCustomers,
        long activeCustomers,
        long totalProducts,
        long lowStockProducts,
        long totalOrders,
        long todayOrders,
        long totalReviews,
        double averageRating
) { }
