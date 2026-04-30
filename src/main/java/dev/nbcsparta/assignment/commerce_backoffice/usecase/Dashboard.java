package dev.nbcsparta.assignment.commerce_backoffice.usecase;

import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.DashboardResponse;
import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.DashboardSummary;
import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.DashboardWidgets;
import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.charts.DashboardCharts;
import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.data.*;
import dev.nbcsparta.assignment.commerce_backoffice.service.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Dashboard {
    private final ManagerService managerService;
    private final CustomerService customerService;
    private final ProductService productService;
    private final OrderService orderService;
    private final ReviewService reviewService;

    public Dashboard(
            ManagerService managerService,
            CustomerService customerService,
            ProductService productService,
            OrderService orderService,
            ReviewService reviewService
    ) {
        this.managerService = managerService;
        this.customerService = customerService;
        this.productService = productService;
        this.orderService = orderService;
        this.reviewService = reviewService;
    }

    @Transactional(readOnly = true)
    public DashboardResponse getDashboard() {
        ManagerStatistics managerStatistics = managerService.getStatistics();
        CustomerStatistics customerStatistics = customerService.getStatistics();
        ProductStatistics productStatistics = productService.getStatistics();
        OrderStatistics orderStatistics = orderService.getStatistics();
        ReviewStatistics reviewStatistics = reviewService.getStatistics();

        DashboardSummary dashboardSummary = new DashboardSummary(
                managerStatistics.totalManagers(),
                managerStatistics.activeManagers(),
                customerStatistics.totalCustomers(),
                customerStatistics.activeCustomers(),
                productStatistics.totalProducts(),
                productStatistics.outOfStockProducts(),
                orderStatistics.totalOrders(),
                orderStatistics.todayOrders(),
                reviewStatistics.totalReviews(),
                reviewStatistics.averageRating()
        );

        DashboardWidgets dashboardWidgets = new DashboardWidgets(
                orderStatistics.totalRevenue(),
                orderStatistics.todayRevenue(),
                orderStatistics.preparingOrders(),
                orderStatistics.shippingOrders(),
                orderStatistics.completedOrders(),
                productStatistics.lowStockProducts(),
                productStatistics.outOfStockProducts()
        );

        DashboardCharts dashboardCharts = new DashboardCharts(
                reviewService.getRatingCount(),
                customerService.getStatusCount(),
                productService.getCategoryCount()
        );

        return new DashboardResponse(
                dashboardSummary,
                dashboardWidgets,
                dashboardCharts,
                orderService.getRecentOrder()
        );
    }
}
