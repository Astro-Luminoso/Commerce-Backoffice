package dev.nbcsparta.assignment.commerce_backoffice.dto;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Manager;

public record MyProfileResponse (
        String name,
        String email,
        String phoneNumber
) {

    public static MyProfileResponse from(Manager manager) {
        return new MyProfileResponse(
                manager.getName(),
                manager.getEmail(),
                manager.getPhoneNumber()
        );
    }
}
