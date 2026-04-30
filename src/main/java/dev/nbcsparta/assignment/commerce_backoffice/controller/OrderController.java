package dev.nbcsparta.assignment.commerce_backoffice.controller;

import dev.nbcsparta.assignment.commerce_backoffice.config.CustomUserDetail;
import dev.nbcsparta.assignment.commerce_backoffice.dto.*;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.ProductStatus;
import dev.nbcsparta.assignment.commerce_backoffice.usecase.OrderAction;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderAction orderAction;

    public OrderController(OrderAction orderAction) {
        this.orderAction = orderAction;
    }

    @PostMapping
    public ResponseEntity<CommonResponse<OrderDetail>> createOrder(
            @Valid @RequestBody CreateOrderRequest request,
            @AuthenticationPrincipal CustomUserDetail userDetails
    ) {
        Long managerId = userDetails.getManagerId();
        OrderDetail response = orderAction.createOrder(request, managerId);

        return CommonResponse
                .success(HttpStatus.CREATED, "주문 생성 성공", response)
                .toResponseEntity();
    }

    @GetMapping
    public ResponseEntity<CommonResponse<OrderListDetail>> getAllOrder(
            @RequestParam(required = false) Long orderId,
            @RequestParam(required = false) String customerName,
            @PageableDefault(sort = "id") Pageable pageable,
            @RequestParam(required = false) ProductStatus status
    ) {
        int fixedPageNumber = Math.max(0, pageable.getPageNumber() - 1);

        // 페이지 넘버를 -1 한 넘버를 가진 Pageable 생성
        Pageable customPageable = PageRequest.of(
                fixedPageNumber,
                pageable.getPageSize(),
                pageable.getSort()
        );

        GetOrderPageFilter filter = new GetOrderPageFilter(orderId, customerName, status);
        OrderListDetail response = orderAction.getOrderListDetail(filter, customPageable);

        return CommonResponse
                .success(HttpStatus.OK, "주문 전체 조회 성공", response)
                .toResponseEntity();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<CommonResponse<OrderDetail>> getOneOrder(
            @PathVariable Long orderId
    ) {
        OrderDetail response = orderAction.getOrderDetail(orderId);

        return CommonResponse
                .success(HttpStatus.OK, "주문 단 건 조회 성공", response)
                .toResponseEntity();
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<CommonResponse<OrderDetail>> updateOrderStatus(
            @Valid @RequestBody UpdateOrderStatusRequest request,
            @PathVariable Long orderId
    ) {
        OrderDetail response = orderAction.updateOrderDetail(orderId, request);

        return CommonResponse
                .success(HttpStatus.OK, "주문 상태 변경 성공", response)
                .toResponseEntity();
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<CommonResponse<Void>> deleteOrder(
            @PathVariable Long orderId
    ) {
        orderAction.delete(orderId);
        return CommonResponse
                .success(HttpStatus.NO_CONTENT, "주문 삭제 성공")
                .toResponseEntity();
    }

}
