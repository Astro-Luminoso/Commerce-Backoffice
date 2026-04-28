package dev.nbcsparta.assignment.commerce_backoffice.service;

import dev.nbcsparta.assignment.commerce_backoffice.dto.CreateCustomerRequest;
import dev.nbcsparta.assignment.commerce_backoffice.dto.CreateOrderRequest;
import dev.nbcsparta.assignment.commerce_backoffice.dto.OrderDetail;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Customer;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Manager;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Order;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Product;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.ProductStatus;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.Role;
import dev.nbcsparta.assignment.commerce_backoffice.repository.ManagerRepository;
import dev.nbcsparta.assignment.commerce_backoffice.repository.OrderRepository;
import dev.nbcsparta.assignment.commerce_backoffice.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ManagerRepository managerRepository;
    @Mock
    private CustomerService customerService;

    @InjectMocks
    private OrderService orderService;



    @Test
    @DisplayName("주문 생성 성공")
    void createOrder_Success() {
        CreateOrderRequest orderRequest = new CreateOrderRequest(
                1L,
                1L,
                5
        );

        Long customerId = 1L;
        Long productId = 1L;
        Long managerId = 1L;

        Manager manager = new Manager(
                "홍길동",
                "qwer@gmail.com",
                "asdf",
                "010-4444-4444",
                Role.Super_MANAGER,
                AccountStatus.ACTIVE
        );

        CreateCustomerRequest customerRequest = new CreateCustomerRequest(
                "양성훈",
                "asdf@naver.com",
                "010-1111-1111"
        );

        Customer customer = Customer.from(customerRequest);

        Product product = new Product(
                "폰",
                "전자제품",
                5000,
                50,
                ProductStatus.SALE,
                manager
        );

        given(productRepository.findById(productId)).willReturn(Optional.of(product));
        given(managerRepository.findById(managerId)).willReturn(Optional.of(manager));

        given(customerService.validateCustomer(customerId)).willReturn(customer);
        given(orderRepository.save(any(Order.class))).willAnswer(invocation -> invocation.getArgument(0));
        OrderDetail response = orderService.createOrder(orderRequest, managerId);

        assertEquals("양성훈", response.customerName());
        assertEquals("홍길동", response.ManagerName());
        assertEquals(25000, response.totalPrice());
    }



}