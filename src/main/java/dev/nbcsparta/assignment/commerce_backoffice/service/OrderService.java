package dev.nbcsparta.assignment.commerce_backoffice.service;

import dev.nbcsparta.assignment.commerce_backoffice.dto.*;
import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.RecentOrderItem;
import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.data.OrderDashboard;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Customer;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Manager;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Order;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Product;
import dev.nbcsparta.assignment.commerce_backoffice.exception.ManagerNotFoundException;
import dev.nbcsparta.assignment.commerce_backoffice.exception.OrderNotFoundException;
import dev.nbcsparta.assignment.commerce_backoffice.exception.ProductNotFoundException;
import dev.nbcsparta.assignment.commerce_backoffice.repository.ManagerRepository;
import dev.nbcsparta.assignment.commerce_backoffice.repository.OrderRepository;
import dev.nbcsparta.assignment.commerce_backoffice.repository.ProductRepository;
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

    // TODO 레포지토리들 전부 서비스로 변경해서 필요한 메서드들 만들어서 사용하기
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
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
    }

    @Transactional
    public OrderDetail createOrder(CreateOrderRequest request, Long managerId) {
        Product product = productRepository.findById(request.productId()).orElseThrow(ProductNotFoundException::new);
        Customer customer = customerService.getCustomerById(request.customerId());
        Manager manager = managerRepository.findById(managerId).orElseThrow(ManagerNotFoundException::new);

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

        int cancelledQuantity = order.getQuantity();

        // TODO 나중에 상품 서비스로 취소 갯수를 가져가서 갯수 추가해주는 코드 추가해주기
        order.getProduct().addQuantity(cancelledQuantity);

        orderRepository.deleteById(orderId);
    }

    @Transactional
    public OrderDashboard getStatistics() {
        LocalDate now = LocalDate.now();
        LocalDateTime start = now.atStartOfDay();
        LocalDateTime end = now.plusDays(1).atStartOfDay().minusNanos(1);

        return orderRepository.getStatistics(start, end);
    }

    @Transactional
    public List<RecentOrderItem> getRecentOrder() {
        return orderRepository.getRecentOrder(PageRequest.of(0,10));
    }
}
