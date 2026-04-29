package dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.charts;

import java.util.List;

public record DashboardCharts(
        List<ReviewRatingCount> reviewRating,
        List<CustomerStatusCount> customerStatus,
        List<ProductCategoryCount> productCategory
) {}
