package dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.data;

public record OrderDashboard(
        long totalOrders,
        long todayOrders,
        long totalRevenue,
        long todayRevenue,
        long preparingOrders,
        long shippingOrders,
        long completedOrders
) {
}