package dev.nbcsparta.assignment.commerce_backoffice.dto;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Customer;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public class CustomerResponse {

    /**
     * 고객 정보 응답 DTO를 리스트로 담는 DTO
     *
     * @param customers 고객들의 정보를 담은 리스트
     * @param pageInfo  페이징 정보가 담긴 객체
     */
    public record ListCustomerResponse(
            List<CustomerInfo> customers,
            PageInfo pageInfo
    ) {
        public static ListCustomerResponse from(Page<Customer> page) {
            List<CustomerInfo> customerInfoList = page.getContent().stream()
                    .map(CustomerInfo::from).toList();
            return new ListCustomerResponse(customerInfoList, PageInfo.from(page));
        }
    }

    /**
     * 고객 정보를 담는 DTO
     *
     * @param id               고객 고유 번호
     * @param name             고객 이름
     * @param email            고객 이메일
     * @param phone            고객 폰 번호
     * @param status           고객 상태
     * @param registrationDate 고객 가입일
     */
    public record CustomerInfo(
            Long id, String name, String email, String phone,
            AccountStatus status, LocalDateTime registrationDate
    ) {
        public static CustomerInfo from(Customer customer) {
            return new CustomerInfo(
                    customer.getId(), customer.getName(), customer.getEmail(),
                    customer.getPhone(), customer.getStatus(),
                    customer.getRegistrationDate()
            );
        }
    }

    /**
     * 페이징 정보를 담는 DTO
     *
     * @param currentPage   현재 페이지
     * @param pageSize      페이지당 개수
     * @param totalElements 전체 개수
     * @param totalPages    전체 페이지 수
     */
    public record PageInfo(int currentPage, int pageSize, long totalElements, long totalPages) {
        public static PageInfo from(Page<?> page) {
            return new PageInfo(
                    page.getNumber() + 1, page.getSize(),
                    page.getTotalElements(), page.getTotalPages()
            );
        }
    }
}
