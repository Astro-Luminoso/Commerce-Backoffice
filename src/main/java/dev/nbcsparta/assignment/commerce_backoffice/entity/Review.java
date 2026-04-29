package dev.nbcsparta.assignment.commerce_backoffice.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@EntityListeners(AuditingEntityListener.class)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Min(1)
    @Max(5)
    private Integer rating;

    @Column(nullable = false, length = 100)
    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public Review() {}

    public Review(Integer rating, String content, Order order, Customer customer, Product product) {
        this.rating = rating;
        this.content = content;
        this.order = order;
        this.customer = customer;
        this.product = product;
    }

    public Long getId() {return id;}

    public Integer getRating() {return rating;}

    public String getContent() {return content;}

    public LocalDateTime getCreatedAt() {return createdAt;}

    public Order getOrder() {return order;}

    public Customer getCustomer() {return customer;}

    public Product getProduct() {return product;}

    public Long getOrderId() {return this.order.getId();}

    public String getCustomerName() {return this.customer.getName();}

    public String getCustomerEmail() {return this.customer.getEmail();}

    public String getProductName() {return this.product.getName();}
}
