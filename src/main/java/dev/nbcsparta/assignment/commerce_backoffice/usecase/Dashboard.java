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
        ManagerDashboard managerDashboard = managerService.getStatistics();
        CustomerDashboard customerDashboard = customerService.getStatistics();
        ProductDashboard productDashboard = productService.getStatistics();
        OrderDashboard orderDashboard = orderService.getStatistics();
        ReviewDashboard reviewDashboard = reviewService.getStatistics();

        DashboardSummary dashboardSummary = new DashboardSummary(
                managerDashboard.totalManagers(),
                managerDashboard.activeManagers(),
                customerDashboard.totalCustomers(),
                customerDashboard.activeCustomers(),
                productDashboard.totalProducts(),
                productDashboard.outOfStockProducts(),
                orderDashboard.totalOrders(),
                orderDashboard.todayOrders(),
                reviewDashboard.totalReviews(),
                reviewDashboard.averageRating()
        );

        DashboardWidgets dashboardWidgets = new DashboardWidgets(
                orderDashboard.totalRevenue(),
                orderDashboard.todayRevenue(),
                orderDashboard.preparingOrders(),
                orderDashboard.shippingOrders(),
                orderDashboard.completedOrders(),
                productDashboard.lowStockProducts(),
                productDashboard.outOfStockProducts()
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
