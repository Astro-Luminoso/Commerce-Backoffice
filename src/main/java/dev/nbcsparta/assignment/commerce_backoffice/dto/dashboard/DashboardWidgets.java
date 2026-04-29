package dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard;

/**
 * @param totalRevenue 총 매출
 * @param todayRevenue 오늘 매출
 * @param preparingOrders 준비중 주문 수
 * @param shippingOrders 배송중 주문 수
 * @param completedOrders 배송완료 주문 수
 * @param lowStockProducts 재고 부족 상품 수 (5개 이하)
 * @param outOfStockProducts 재고 없음(품절) 상품 수
 */
public record DashboardWidgets(
        long totalRevenue,
        long todayRevenue,
        long preparingOrders,
        long shippingOrders,
        long completedOrders,
        long lowStockProducts,
        long outOfStockProducts
) {}
