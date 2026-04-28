package dev.nbcsparta.assignment.commerce_backoffice.dto;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Customer;
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
    public static CustomerListDetail from(Page<Customer> page) {
        List<CustomerDetail> customerDetailList = page.getContent().stream()
                .map(CustomerDetail::from).toList();

        return new CustomerListDetail(customerDetailList, PageInfo.from(page));
    }
}
