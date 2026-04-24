package dev.nbcsparta.assignment.commerce_backoffice.dto_yang;

import dev.nbcsparta.assignment.commerce_backoffice.entity_yang.Customer;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public class CustomerResponse {

    public record ListCustomerResponse(
            List<CustomerInfo> data,
            PageInfo pageInfo
    ) {
        public static ListCustomerResponse from(Page<Customer> page) {
            List<CustomerInfo> customerInfoList = page.getContent().stream()
                    .map(CustomerInfo::from).toList();
            return new ListCustomerResponse(customerInfoList, PageInfo.from(page));
        }
    }

    public record CustomerInfo(
            Long id, String name, String email, String phone,
            String status, LocalDateTime registrationDate
    ) {
        public static CustomerInfo from(Customer customer) {
            return new CustomerInfo(
                    customer.getId(), customer.getName(), customer.getEmail(),
                    customer.getPhone(), customer.getStatus().getDescription(),
                    customer.getRegistrationDate()
            );
        }
    }

    public record PageInfo(int currentPage, int pageSize, long totalElements, long totalPages) {
        public static PageInfo from(Page<?> page) {
            return new PageInfo(
                    page.getNumber() + 1, page.getSize(),
                    page.getTotalElements(), page.getTotalPages()
            );
        }
    }
}
