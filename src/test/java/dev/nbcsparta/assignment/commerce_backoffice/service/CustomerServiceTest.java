package dev.nbcsparta.assignment.commerce_backoffice.service;

import dev.nbcsparta.assignment.commerce_backoffice.dto.CustomerDetail;
import dev.nbcsparta.assignment.commerce_backoffice.dto.CustomerStatusResponse;
import dev.nbcsparta.assignment.commerce_backoffice.dto.UpdateCustomerDetailRequest;
import dev.nbcsparta.assignment.commerce_backoffice.dto.UpdateCustomerStatusRequest;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Customer;
import dev.nbcsparta.assignment.commerce_backoffice.exception.CustomerNotFoundException;
import dev.nbcsparta.assignment.commerce_backoffice.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    @DisplayName("존재하는 ID로 조회 시 고객 상세 정보 반환")
    void findCustomer_Success() {
        Long customerId = 1L;
        Customer customer = new Customer(
                "홍길동",
                "asdf@naver.com",
                "010-0000-0000",
                AccountStatus.ACTIVE
        );

        given(customerRepository.findById(customerId)).willReturn(Optional.of(customer));

        CustomerDetail response = customerService.findOneCustomer(customerId);

        assertEquals("홍길동", response.name());
        assertEquals("asdf@naver.com", response.email());
        assertEquals("010-0000-0000", response.phone());
        assertEquals(AccountStatus.ACTIVE, response.status());
    }

    @Test
    @DisplayName("존재하지 않는 ID로 조회 시 예외가 발생해야 한다")
    void findCustomer_Failure() {
        given(customerRepository.findById(anyLong())).willReturn(Optional.empty());

        // IllegalStateException = 999번 고객은 없다고 생각하고 찾아봤을때 뜨는 에러
        assertThrows(CustomerNotFoundException.class,
                () -> customerService.findOneCustomer(999L));
    }

    @Test
    @DisplayName("유저 정보 업데이트 성공")
    void updateCustomer_Success() {
        Long customerId = 1L;

        Customer customer = new Customer(
                "홍길동",
                "asdf@naver.com",
                "010-0000-0000",
                AccountStatus.ACTIVE
        );

        given(customerRepository.findById(customerId)).willReturn(Optional.of(customer));

        UpdateCustomerDetailRequest request =
                new UpdateCustomerDetailRequest("양성훈", "qwer@naver.com", "010-4444-4444");

        CustomerDetail response = customerService.updateDetail(customerId, request);

        assertEquals("양성훈", response.name());
        assertEquals("qwer@naver.com", response.email());
        assertEquals("010-4444-4444", response.phone());
        assertEquals(AccountStatus.ACTIVE, response.status());
    }

    @Test
    @DisplayName("유저 상태 수정 성공")
    void updateCustomerStatus_Success() {
        Long customerId = 1L;

        Customer customer = new Customer(
                "홍길동",
                "asdf@naver.com",
                "010-0000-0000",
                AccountStatus.ACTIVE
        );

        given(customerRepository.findById(customerId)).willReturn(Optional.of(customer));

        // 여기서 요청은 주소창에 적는 String 타입
        UpdateCustomerStatusRequest request =
                new UpdateCustomerStatusRequest("비활성");

        // String 타입을 Enum으로 변경하여 업데이트 진행후 응답으로는 String으로 돌려줌
        CustomerStatusResponse response = customerService.updateStatus(customerId, request);

        // String으로 돌려줬기 때문에 Enum으로 변경하고 비교 진행
        assertEquals(AccountStatus.INACTIVE, AccountStatus.getEnum(response.status()));
        assertEquals("비활성", response.status());
    }

}