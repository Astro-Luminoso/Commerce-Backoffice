package dev.nbcsparta.assignment.commerce_backoffice.controller;

import dev.nbcsparta.assignment.commerce_backoffice.config.Authentication;
import dev.nbcsparta.assignment.commerce_backoffice.dto.CreateOrderRequest;
import dev.nbcsparta.assignment.commerce_backoffice.dto.CreateOrderResponse;
import dev.nbcsparta.assignment.commerce_backoffice.dto.SessionManager;
import dev.nbcsparta.assignment.commerce_backoffice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final Authentication authentication;

    public OrderController(OrderService orderService, Authentication authentication) {
        this.orderService = orderService;
        this.authentication = authentication;
    }

    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
        SessionManager sessionManager = authentication.getCurrentManager();
        CreateOrderResponse response = orderService.createOrder(request, sessionManager.id());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
