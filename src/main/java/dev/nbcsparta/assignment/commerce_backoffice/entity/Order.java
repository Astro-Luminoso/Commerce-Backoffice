package dev.nbcsparta.assignment.commerce_backoffice.entity;

import dev.nbcsparta.assignment.commerce_backoffice.dto.order.CancelOrderRequest;
import dev.nbcsparta.assignment.commerce_backoffice.dto.order.CreateOrderRequest;
import dev.nbcsparta.assignment.commerce_backoffice.dto.order.UpdateOrderStatusRequest;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.DeliveryStatus;
import dev.nbcsparta.assignment.commerce_backoffice.exception.AlreadyProcessingOrderException;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer totalPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime orderDate;

    private String cancelReason;

    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private boolean isDeleted;

    protected Order() {
    }

    public Order(CreateOrderRequest request, Customer customer, Manager manager, Product product) {
        this.quantity = request.quantity();
        this.totalPrice = product.getPrice() * request.quantity();
        this.deliveryStatus = DeliveryStatus.PENDING;
        this.isDeleted = false;
        this.customer = customer;
        this.manager = manager;
        this.product = product;
        this.isDeleted = false;
    }

    public void updateStatus(UpdateOrderStatusRequest request) {
        this.deliveryStatus = request.status();
    }

    public void cancelOrder(CancelOrderRequest request) {
        if (this.deliveryStatus != DeliveryStatus.PENDING) {
            throw new AlreadyProcessingOrderException();
        }
        this.isDeleted = true;
        this.cancelReason = request.reason();
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Manager getManager() {
        return manager;
    }

    public Product getProduct() {
        return product;
    }

    public void toggleDeleted() {
        this.isDeleted = !this.isDeleted;
    }
}
