package dev.nbcsparta.assignment.commerce_backoffice.dto.manager;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Manager;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.Role;

public record CreateManagerResponse (
        Long id,
        String name,
        String email,
        String phoneNumber,
        Role role
) {

    public static CreateManagerResponse from(Manager manager) {
        return new CreateManagerResponse(
                manager.getId(),
                manager.getName(),
                manager.getEmail(),
                manager.getPhoneNumber(),
                manager.getRole()
        );
    }
}
