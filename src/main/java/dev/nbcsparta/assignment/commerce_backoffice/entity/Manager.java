package dev.nbcsparta.assignment.commerce_backoffice.entity;

import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.Role;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private AccountStatus status;

    @Column(nullable = false)
    private Role role;

    @CreatedDate
    private LocalDateTime registrationDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    protected Manager() {
    }

    public Manager(String name, String email, String password, String phoneNumber, AccountStatus status) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public Manager(String name, String email, String password, String phoneNumber) {
        this(name, email, password, phoneNumber, AccountStatus.PENDING);
    }

    public long getId(){
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public AccountStatus getStatus() {
        return status;
    }
}
