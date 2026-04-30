package dev.nbcsparta.assignment.commerce_backoffice.usecase;

import dev.nbcsparta.assignment.commerce_backoffice.dto.*;
import dev.nbcsparta.assignment.commerce_backoffice.dto.order.*;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Customer;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Manager;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Order;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Product;
import dev.nbcsparta.assignment.commerce_backoffice.service.CustomerService;
import dev.nbcsparta.assignment.commerce_backoffice.service.ManagerService;
import dev.nbcsparta.assignment.commerce_backoffice.service.OrderService;
import dev.nbcsparta.assignment.commerce_backoffice.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class OrderAction {

    private final ManagerService managerService;
    private final ProductService productService;
    private final CustomerService customerService;
    private final OrderService orderService;

    public OrderAction(
            ManagerService managerService,
            ProductService productService,
            CustomerService customerService,
            OrderService orderService
    ) {
        this.managerService = managerService;
        this.productService = productService;
        this.customerService = customerService;
        this.orderService = orderService;
    }

    @Transactional(readOnly = true)
    public OrderListDetail getOrderListDetail(GetOrderPageFilter filter, Pageable pageable) {
        Page<Order> order = orderService.findAllOrder(filter, pageable);
        return OrderListDetail.from(order);
    }

    public OrderDetail createOrder(CreateOrderRequest request, Long managerId) {
        Product product = productService.getProductById(request.productId());
        productService.deductQuantity(product, request.quantity());
        Customer customer = customerService.getCustomerById(request.customerId());
        Manager manager = managerService.getManagerById(managerId);

        Order order = orderService.orderProduct(customer, manager, product, request);

        return OrderDetail.from(order);
    }

    @Transactional(readOnly = true)
    public OrderDetail getOrderDetail(Long orderId) {
        Order order = orderService.getOrderById(orderId);
        return OrderDetail.from(order);
    }

    public OrderDetail updateOrderDetail(Long orderId, UpdateOrderStatusRequest request) {
        Order order = orderService.updateStatus(request, orderId);
        return OrderDetail.from(order);
    }

    public void delete(Long orderId, CancelOrderRequest request) {
        Order order = orderService.getOrderById(orderId);
        productService.addQuantity(order.getProduct().getId(), order.getQuantity());
        orderService.softDelete(order, request);
    }
}
