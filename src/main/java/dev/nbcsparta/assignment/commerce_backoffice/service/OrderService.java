package dev.nbcsparta.assignment.commerce_backoffice.service;

import dev.nbcsparta.assignment.commerce_backoffice.dto.*;
import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.RecentOrderItem;
import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.data.OrderDashboard;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Customer;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Manager;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Order;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Product;
import dev.nbcsparta.assignment.commerce_backoffice.exception.OrderNotFoundException;
import dev.nbcsparta.assignment.commerce_backoffice.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ManagerService managerService;
    private final ProductService productService;
    private final CustomerService customerService;

    public OrderService(
            OrderRepository orderRepository,
            ManagerService managerService,
            ProductService productService,
            CustomerService customerService
    ) {
        this.orderRepository = orderRepository;
        this.managerService = managerService;
        this.productService = productService;
        this.customerService = customerService;
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
    }

    @Transactional
    public OrderDetail createOrder(CreateOrderRequest request, Long managerId) {
        Product product = productService.getProductById(request.productId());
        Customer customer = customerService.getCustomerById(request.customerId());
        Manager manager = managerService.getManagerById(managerId);

        // 재고 차감 진행
        product.buy(request.quantity());

        Order order = new Order(request, customer, manager, product);

        orderRepository.save(order);

        return OrderDetail.from(order);
    }

    @Transactional(readOnly = true)
    public OrderListDetail findAllOrder(
            GetOrderPageFilter filter,
            Pageable customPageable
    ) {
        Page<Order> orderPage = orderRepository.findAllByFilters(filter, customPageable);

        return OrderListDetail.from(orderPage);
    }

    @Transactional(readOnly = true)
    public OrderDetail getDetailOrder(Long orderId) {
        Order order = getOrderById(orderId);

        return OrderDetail.from(order);
    }

    @Transactional
    public void updateStatus(UpdateOrderStatusRequest request, Long orderId) {
        Order order = getOrderById(orderId);

        order.updateStatus(request);
    }

    @Transactional
    public void delete(Long orderId) {
        Order order = getOrderById(orderId);
        Long productId = order.getProduct().getId();
        int cancelledQuantity = order.getQuantity();

        productService.addQuantity(productId, cancelledQuantity);

        orderRepository.deleteById(orderId);
    }

    public OrderDashboard getStatistics() {
        LocalDate now = LocalDate.now();
        LocalDateTime start = now.atStartOfDay();
        LocalDateTime end = now.plusDays(1).atStartOfDay().minusNanos(1);

        return orderRepository.getStatistics(start, end);
    }

    public List<RecentOrderItem> getRecentOrder() {
        return orderRepository.getRecentOrder(PageRequest.of(0,10));
    }
}
