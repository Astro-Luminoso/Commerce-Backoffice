package dev.nbcsparta.assignment.commerce_backoffice.dto.response;

import dev.nbcsparta.assignment.commerce_backoffice.type.ManagerRole;

public record CreateManagerResponse (
        String name,
        String email,
        String phone,
        ManagerRole role
) {
}
