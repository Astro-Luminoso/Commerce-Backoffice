package dev.nbcsparta.assignment.commerce_backoffice.service;

import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.DashboardResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DashboardService {
    private final ManagerService managerService;
    private final CustomerService customerService;
    private final ProductService productService;
    private final OrderService orderService;
    private final ReviewService reviewService;

    public DashboardService(
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

        return new DashboardResponse();
    }
}
