package dev.nbcsparta.assignment.commerce_backoffice.controller;

import dev.nbcsparta.assignment.commerce_backoffice.config.Authentication;
import dev.nbcsparta.assignment.commerce_backoffice.service.DashboardService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

    private final DashboardService dashboardService;
    private final Authentication authentication;

    public DashboardController(DashboardService dashboardService, Authentication authentication) {
        this.dashboardService = dashboardService;
        this.authentication = authentication;
    }
}
