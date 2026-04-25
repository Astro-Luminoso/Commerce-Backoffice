package dev.nbcsparta.assignment.commerce_backoffice.dto;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Customer;

public record CustomerStatusResponse(Long id, String status) {
    public static CustomerStatusResponse from(Customer customer) {
        return new CustomerStatusResponse(
                customer.getId(),
                customer.getStatus().getDescription()
        );
    }
}
