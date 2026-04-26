package dev.nbcsparta.assignment.commerce_backoffice.dto;

import dev.nbcsparta.assignment.commerce_backoffice.enumerate.Role;
import jakarta.validation.constraints.NotNull;

public record ManagerRoleUpdate(
        @NotNull
        Role role
) {
}
