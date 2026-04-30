package dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.charts;

import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;

public record CustomerStatusCount(
        AccountStatus status,
        long count
) {
}
