package dev.nbcsparta.assignment.commerce_backoffice.entity;

import dev.nbcsparta.assignment.commerce_backoffice.enumerate.ProductStatus;
import dev.nbcsparta.assignment.commerce_backoffice.exception.OutOfStockException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;
    private String category;

    @NotNull
    private int price;

    @NotNull
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "manager_id", nullable = false)
    private Manager manager;

    public Product(String name, String category, int price, int quantity, ProductStatus status, Manager manager) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.manager = manager;
    }

    public void update(String name, String category, int price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public void buyProduct(int quantity) {
        if(this.quantity<quantity){
            throw new OutOfStockException();
        }
        this.quantity = quantity;
        if(this.quantity == 0) {
            this.status = ProductStatus.SOLD_OUT;
        }
    }

    public Product() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public Manager getManager() {
        return manager;
    }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

}
