package dev.nbcsparta.assignment.commerce_backoffice.entity;

import dev.nbcsparta.assignment.commerce_backoffice.dto.CreateCustomerRequest;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected Customer() {
        super();
    }

    private Customer(String name, String email, String phoneNumber, AccountStatus status) {
        super(name, email, phoneNumber, status);
    }

    public static Customer from(CreateCustomerRequest request) {
        return new Customer(
                request.name(),
                request.email(),
                request.phoneNumber(),
                AccountStatus.ACTIVE
        );
    }

    public Long getId() {
        return id;
    }
}
