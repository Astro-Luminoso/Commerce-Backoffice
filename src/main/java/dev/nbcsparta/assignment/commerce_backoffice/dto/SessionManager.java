package dev.nbcsparta.assignment.commerce_backoffice.dto;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Manager;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.Role;

public record SessionManager (
        Long id,
        String email,
        Role role
) {
    public static SessionManager from(Manager manager) {
        return new SessionManager(
          manager.getId(),
          manager.getEmail(),
          manager.getRole()
        );
    }
}
