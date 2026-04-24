package dev.nbcsparta.assignment.commerce_backoffice.dto.response;

import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.Role;

public record CreateManagerResponse (
        String name,
        String email,
        String phone,
        Role role,
        AccountStatus status
) {
}
