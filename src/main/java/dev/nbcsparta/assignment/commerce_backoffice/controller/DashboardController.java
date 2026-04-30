package dev.nbcsparta.assignment.commerce_backoffice.controller;

import dev.nbcsparta.assignment.commerce_backoffice.config.Authentication;
import dev.nbcsparta.assignment.commerce_backoffice.dto.CommonResponse;
import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.DashboardResponse;
import dev.nbcsparta.assignment.commerce_backoffice.usecase.Dashboard;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

    private final Dashboard dashboard;
    private final Authentication authentication;

    public DashboardController(Dashboard dashboard, Authentication authentication) {
        this.dashboard = dashboard;
        this.authentication = authentication;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<CommonResponse<DashboardResponse>> getDashboard() {
        authentication.getCurrentManager();
        DashboardResponse res = dashboard.getDashboard();

        return CommonResponse
                .success(HttpStatus.OK, "대시보드 조회 성공", res)
                .toResponseEntity();
    }
}
