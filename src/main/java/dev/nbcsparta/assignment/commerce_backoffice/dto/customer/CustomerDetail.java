package dev.nbcsparta.assignment.commerce_backoffice.dto.customer;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Customer;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;

import java.time.LocalDateTime;

/**
 * 고객 정보를 담는 DTO
 *
 * @param id               고객 고유 번호
 * @param name             고객 이름
 * @param email            고객 이메일
 * @param phoneNumber      고객 폰 번호
 * @param status           고객 상태
 * @param registrationDate 고객 가입일
 */
public record CustomerDetail(
        Long id,
        String name,
        String email,
        String phoneNumber,
        Long totalOrderCount,
        Long totalOrderPrice,
        AccountStatus status,
        LocalDateTime registrationDate
) {
    public CustomerDetail(
            Long id,
            String name,
            String email,
            String phoneNumber,
            // 레포지토리에서 SUM을 사용해서 나오는 값이 BigInteger로 나오는것을 확인하였습니다.
            // Number로 받아서 long으로 형변환 해주었습니다.
            Number totalOrderCount,
            Number totalOrderPrice,
            AccountStatus status,
            LocalDateTime registrationDate
    ) {
        this(
                id,
                name,
                email,
                phoneNumber,
                totalOrderCount != null ? totalOrderCount.longValue() : 0L,
                totalOrderPrice != null ? totalOrderPrice.longValue() : 0L,
                status,
                registrationDate
        );
    }

    public static CustomerDetail from(Customer customer) {
        return new CustomerDetail(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                0L,
                0L,
                customer.getStatus(),
                customer.getRegistrationDate()
        );
    }
}
