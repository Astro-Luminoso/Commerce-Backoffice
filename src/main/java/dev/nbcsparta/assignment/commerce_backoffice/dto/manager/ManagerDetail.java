package dev.nbcsparta.assignment.commerce_backoffice.dto.manager;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Manager;

public record ManagerDetail(
        long id,
        String name,
        String email,
        String phoneNumber,
        String role,
        String accountStatus,
        String registrationDate,
        String updatedDate) {

    public static ManagerDetail from(Manager manager) {
        return new ManagerDetail(
                manager.getId(),
                manager.getName(),
                manager.getEmail(),
                manager.getPhoneNumber(),
                manager.getRole().getRoleName(),
                manager.getStatus().getDescription(),
                manager.getRegistrationDate().toString(),
                manager.getUpdatedDate().toString()
        );
    }
}
