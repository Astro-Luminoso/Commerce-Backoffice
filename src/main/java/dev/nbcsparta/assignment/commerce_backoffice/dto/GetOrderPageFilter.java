package dev.nbcsparta.assignment.commerce_backoffice.dto;

import dev.nbcsparta.assignment.commerce_backoffice.enumerate.ProductStatus;

public record GetOrderPageFilter(Long orderId, String customerName, ProductStatus status) {
}
