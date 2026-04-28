package dev.nbcsparta.assignment.commerce_backoffice.entity;

import dev.nbcsparta.assignment.commerce_backoffice.dto.CreateCustomerRequest;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.Formula;

@Entity
@Table(name = "customers")
public class Customer extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Formula("(SELECT COUNT(*) FROM orders o WHERE o.customer_id = id)")
    private Integer totalOrderCount;

    @Formula("(SELECT COALESCE(SUM(o.total_price), 0) FROM orders o WHERE o.customer_id = id)")
    private Integer totalOrderPrice;

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

    public Integer getTotalOrderCount() {
        return totalOrderCount != null ? totalOrderCount : 0;
    }

    public Integer getTotalOrderPrice() {
        return totalOrderPrice != null ? totalOrderPrice : 0;
    }
}
