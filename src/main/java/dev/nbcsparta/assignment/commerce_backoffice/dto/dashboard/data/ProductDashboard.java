package dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.data;

public record ProductDashboard(
        long totalProducts,
        long lowStockProducts,
        long outOfStockProducts
) {
}