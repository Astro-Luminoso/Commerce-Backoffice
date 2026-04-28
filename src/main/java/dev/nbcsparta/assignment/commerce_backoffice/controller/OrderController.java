package dev.nbcsparta.assignment.commerce_backoffice.controller;

import dev.nbcsparta.assignment.commerce_backoffice.config.Authentication;
import dev.nbcsparta.assignment.commerce_backoffice.dto.*;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.ProductStatus;
import dev.nbcsparta.assignment.commerce_backoffice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<OrderDetail> createOrder(
            @Valid
            @RequestBody
            CreateOrderRequest request
    ) {
        SessionManager sessionManager = authentication.getCurrentManager();

        OrderDetail response = orderService.createOrder(request, sessionManager.id());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<OrderListDetail> getAllOrder(
            @RequestParam(required = false)
            Long orderId,

            @RequestParam(required = false)
            String customerName,

            @PageableDefault(sort = "quantity")
            Pageable pageable,

            @RequestParam(required = false)
            ProductStatus status
    ) {
        int fixedPageNumber = Math.max(0, pageable.getPageNumber() - 1);

        // 페이지 넘버를 -1 한 넘버를 가진 Pageable 생성
        Pageable customPageable = PageRequest.of(
                fixedPageNumber,
                pageable.getPageSize(),
                pageable.getSort()
        );

        OrderListDetail response =
                orderService.findAllOrder(orderId, customerName, customPageable, status);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetail> getOneOrder(
            @PathVariable
            Long orderId
    ) {
        OrderDetail response = orderService.getDetailOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<Void> updateOrderStatus(
            @Valid
            @RequestBody
            UpdateOrderStatusRequest request,

            @PathVariable
            Long orderId
    ) {
        orderService.updateStatus(request, orderId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable Long orderId) {
        orderService.delete(orderId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
