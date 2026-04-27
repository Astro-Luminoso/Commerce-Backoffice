package dev.nbcsparta.assignment.commerce_backoffice.entity;

import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
public class Customer extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    protected Customer() {
        super();
    }

    public Customer(String name, String email, String phoneNumber, AccountStatus status) {
        super(name, email, phoneNumber, status);
    }

    public Long getId() {
        return id;
    }
}
