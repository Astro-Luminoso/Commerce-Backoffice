package dev.nbcsparta.assignment.commerce_backoffice.service;

import dev.nbcsparta.assignment.commerce_backoffice.dto.CreateOrderRequest;
import dev.nbcsparta.assignment.commerce_backoffice.dto.CreateOrderResponse;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Customer;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Manager;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Order;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Product;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.DeliveryStatus;
import dev.nbcsparta.assignment.commerce_backoffice.exception.ManagerNotFoundException;
import dev.nbcsparta.assignment.commerce_backoffice.exception.ProductNotFoundException;
import dev.nbcsparta.assignment.commerce_backoffice.repository.ManagerRepository;
import dev.nbcsparta.assignment.commerce_backoffice.repository.OrderRepository;
import dev.nbcsparta.assignment.commerce_backoffice.repository.ProductRepository;
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
        return orderRepository.findById(orderId).orElseThrow(ProductNotFoundException::new);
    }

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderRequest request, Long managerId) {
        Product product = productRepository.findById(request.productId()).orElseThrow(ProductNotFoundException::new);
        Customer customer = customerService.validateCustomer(request.customerId());

        Manager manager = null;
        if (managerId != null)
            manager = managerRepository.findById(managerId).orElseThrow(ManagerNotFoundException::new);


        long totalPrice = product.getPrice() * request.quantity();

        Order order = new Order(
                request.quantity(),
                totalPrice,
                DeliveryStatus.PENDING,
                customer,
                manager,
                product
        );

        Order savedOrder = orderRepository.save(order);

        return CreateOrderResponse.from(savedOrder);
    }
}
