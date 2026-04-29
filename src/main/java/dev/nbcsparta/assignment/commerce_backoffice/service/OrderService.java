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

    public OrderService( OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
    }

    @Transactional
    public Order orderProduct(
            Customer customer,
            Manager manager,
            Product product,
            CreateOrderRequest request
    ) {
        // 재고 차감 진행
        product.buy(request.quantity());
        Order order = new Order(request, customer, manager, product);

        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public Page<Order> findAllOrder(
            GetOrderPageFilter filter,
            Pageable customPageable
    ) {
        return orderRepository.findAllByFilters(filter, customPageable);
    }

    @Transactional
    public Order updateStatus(UpdateOrderStatusRequest request, Long orderId) {
        Order order = getOrderById(orderId);
        order.updateStatus(request);

        return order;
    }

    @Transactional
    public void softDelete(Order order) {
        order.toggleDeleted();
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
