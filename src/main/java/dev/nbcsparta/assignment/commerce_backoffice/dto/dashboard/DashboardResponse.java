package dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard;

import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.charts.DashboardCharts;

import java.util.List;

public record DashboardResponse(
        DashboardSummary summary,
        DashboardWidgets widgets,
        DashboardCharts charts,
        List<RecentOrderItem> recentOrders
) {}
