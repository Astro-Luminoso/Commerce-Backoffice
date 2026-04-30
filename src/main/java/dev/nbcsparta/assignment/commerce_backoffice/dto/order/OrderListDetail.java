package dev.nbcsparta.assignment.commerce_backoffice.dto.order;

import dev.nbcsparta.assignment.commerce_backoffice.dto.PageInfo;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Order;
import org.springframework.data.domain.Page;

import java.util.List;

public record OrderListDetail(
        List<OrderDetail> orders,
        PageInfo pageInfo
) {
    public static OrderListDetail from(Page<Order> page) {
        List<OrderDetail> orderDetailList = page.getContent().stream()
                .map(OrderDetail::from).toList();

        return new OrderListDetail(orderDetailList, PageInfo.from(page));
    }

}
