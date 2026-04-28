package dev.nbcsparta.assignment.commerce_backoffice.dto;

import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;

public record PageFilter(String name, String email, AccountStatus status) {
}
