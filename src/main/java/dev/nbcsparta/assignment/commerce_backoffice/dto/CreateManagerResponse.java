package dev.nbcsparta.assignment.commerce_backoffice.dto;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Manager;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.Role;

import java.time.LocalDateTime;

public record CreateManagerResponse (
        Long id,
        String name,
        String email,
        String phoneNumber,
        Role role,
        LocalDateTime registrationDate
) {

    public static CreateManagerResponse from(Manager manager) {
        return new CreateManagerResponse(
                manager.getId(),
                manager.getName(),
                manager.getEmail(),
                manager.getPhoneNumber(),
                manager.getRole(),
                manager.getRegistrationDate()
        );
    }
}
