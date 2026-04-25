package dev.nbcsparta.assignment.commerce_backoffice.entity;

import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 16)
    private String phone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Column(name = "registration_date", nullable = false, updatable = false)
    private LocalDateTime registrationDate;

    protected Customer() {
    }

    public Customer(String name, String email, String phone, AccountStatus status) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.registrationDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }
}
