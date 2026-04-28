package dev.nbcsparta.assignment.commerce_backoffice.service;

import dev.nbcsparta.assignment.commerce_backoffice.dto.CreateOrderRequest;
import dev.nbcsparta.assignment.commerce_backoffice.dto.OrderDetail;
import dev.nbcsparta.assignment.commerce_backoffice.dto.OrderListDetail;
import dev.nbcsparta.assignment.commerce_backoffice.dto.UpdateOrderStatusRequest;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Customer;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Manager;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Order;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Product;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.DeliveryStatus;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.ProductStatus;
import dev.nbcsparta.assignment.commerce_backoffice.exception.ManagerNotFoundException;
import dev.nbcsparta.assignment.commerce_backoffice.exception.OrderNotFoundException;
import dev.nbcsparta.assignment.commerce_backoffice.exception.ProductNotFoundException;
import dev.nbcsparta.assignment.commerce_backoffice.repository.ManagerRepository;
import dev.nbcsparta.assignment.commerce_backoffice.repository.OrderRepository;
import dev.nbcsparta.assignment.commerce_backoffice.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ManagerRepository managerRepository;
    private final ProductRepository productRepository;
    private final CustomerService customerService;

    public OrderService(
            OrderRepository orderRepository,
            ManagerRepository managerRepository,
            ProductRepository productRepository,
            CustomerService customerService
    ) {
        this.orderRepository = orderRepository;
        this.managerRepository = managerRepository;
        this.productRepository = productRepository;
        this.customerService = customerService;
    }

    @Transactional
    public Order validateOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
    }

    @Transactional
    public OrderDetail createOrder(CreateOrderRequest request, Long managerId) {
        Product product = productRepository.findById(request.productId()).orElseThrow(ProductNotFoundException::new);
        Customer customer = customerService.validateCustomer(request.customerId());
        Manager manager = managerRepository.findById(managerId).orElseThrow(ManagerNotFoundException::new);

        // 재고 차감 진행
        product.buy(request.quantity());

        int totalPrice = product.getPrice() * request.quantity();

        Order order = new Order(
                request.quantity(),
                totalPrice,
                DeliveryStatus.PENDING,
                customer,
                manager,
                product
        );

        Order savedOrder = orderRepository.save(order);

        return OrderDetail.from(savedOrder);
    }

    @Transactional(readOnly = true)
    public OrderListDetail findAllOrder(
            Long orderId,
            String customerName,
            Pageable customPageable,
            ProductStatus status
    ) {
        Page<Order> orderPage = orderRepository.findAllByFilters(orderId, customerName, customPageable, status);

        return OrderListDetail.from(orderPage);
    }

    @Transactional(readOnly = true)
    public OrderDetail getDetailOrder(Long orderId) {
        Order order = validateOrder(orderId);

        return OrderDetail.from(order);
    }

    @Transactional
    public void updateStatus(UpdateOrderStatusRequest request, Long orderId) {
        Order order = validateOrder(orderId);

        order.updateStatus(request.status());
    }

    @Transactional
    public void delete(Long orderId) {
        Order order = validateOrder(orderId);
        int canceledQuantity = order.getQuantity();
        order.getProduct().addQuantity(canceledQuantity);

        orderRepository.deleteById(orderId);
    }
}
