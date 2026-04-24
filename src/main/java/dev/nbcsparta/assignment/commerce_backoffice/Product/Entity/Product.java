package dev.nbcsparta.assignment.commerce_backoffice.Product.Entity;

import dev.nbcsparta.assignment.commerce_backoffice.Manager.Entity.Manager;
import dev.nbcsparta.assignment.commerce_backoffice.Product.Dto.CreateProductRequest;
import dev.nbcsparta.assignment.commerce_backoffice.config.ProductStatus;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "products")
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    private Long price;
    private Long quantity;
    private ProductStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "manager_id", nullable = false)
    private Manager manager;

    public Product(String name, String category, Long price, Long quantity, ProductStatus status, Manager manager) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.manager = manager;
    }

    public static Product of(CreateProductRequest req, Manager manager) {
        return new Product(
                req.name(),
                req.category(),
                req.price(),
                req.quantity(),
                req.status(),
                manager
        );
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

    public Long getPrice() {
        return price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public Manager getManager() {
        return manager;
    }
}
