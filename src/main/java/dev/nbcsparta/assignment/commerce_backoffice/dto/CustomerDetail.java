package dev.nbcsparta.assignment.commerce_backoffice.dto;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Customer;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;

import java.time.LocalDateTime;

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
public record CustomerDetail(
        Long id, String name, String email, String phone,
        AccountStatus status, LocalDateTime registrationDate
) {
    public static CustomerDetail from(Customer customer) {
        return new CustomerDetail(
                customer.getId(), customer.getName(), customer.getEmail(),
                customer.getPhoneNumber(), customer.getStatus(),
                customer.getRegistrationDate()
        );
    }
}
