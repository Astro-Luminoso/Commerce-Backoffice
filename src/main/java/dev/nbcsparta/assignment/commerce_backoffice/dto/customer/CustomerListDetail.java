package dev.nbcsparta.assignment.commerce_backoffice.dto.customer;

import dev.nbcsparta.assignment.commerce_backoffice.dto.PageInfo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 고객 정보 응답 DTO를 리스트로 담는 DTO
 *
 * @param customers 고객들의 정보를 담은 리스트
 * @param pageInfo  페이징 정보가 담긴 객체
 */
public record CustomerListDetail(
        List<CustomerDetail> customers,
        PageInfo pageInfo
) {
    public static CustomerListDetail from(Page<CustomerDetail> page) {
        return new CustomerListDetail(page.getContent(), PageInfo.from(page));
    }
}
