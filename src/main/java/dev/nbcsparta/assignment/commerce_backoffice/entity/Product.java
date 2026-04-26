package dev.nbcsparta.assignment.commerce_backoffice.entity;


import dev.nbcsparta.assignment.commerce_backoffice.config.BaseEntity;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.ProductStatus;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "products")
@EntityListeners(AuditingEntityListener.class)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    private Long price;
    private Long quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
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

    public void update(String name, String category, Long price) {
        this.name = name;
        this.category = category;
        this.price = price;
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

    public void setStatus(ProductStatus status) {
        this.status = status;
    }
}
