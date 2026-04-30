package dev.nbcsparta.assignment.commerce_backoffice.controller;

import dev.nbcsparta.assignment.commerce_backoffice.config.Authentication;
import dev.nbcsparta.assignment.commerce_backoffice.config.CustomUserDetail;
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

    public DashboardController(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<CommonResponse<DashboardResponse>> getDashboard() {
        DashboardResponse res = dashboard.getDashboard();

        return CommonResponse
                .success(HttpStatus.OK, "대시보드 조회 성공", res)
                .toResponseEntity();
    }
}
