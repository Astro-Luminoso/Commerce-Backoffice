package dev.nbcsparta.assignment.commerce_backoffice.service;

import org.springframework.stereotype.Service;

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
}
