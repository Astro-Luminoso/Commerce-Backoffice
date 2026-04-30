package dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.data;

public record ProductStatistics(
        long totalProducts,
        long lowStockProducts,
        long outOfStockProducts
) {
}