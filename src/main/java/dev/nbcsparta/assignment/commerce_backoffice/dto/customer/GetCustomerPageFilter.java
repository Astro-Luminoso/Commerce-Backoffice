package dev.nbcsparta.assignment.commerce_backoffice.dto.customer;

import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;

public record GetCustomerPageFilter(String name, String email, AccountStatus status) {
}
