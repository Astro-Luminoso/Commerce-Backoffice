package dev.nbcsparta.assignment.commerce_backoffice.service;

import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;

import dev.nbcsparta.assignment.commerce_backoffice.dto.CustomerResponse;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Customer;
import dev.nbcsparta.assignment.commerce_backoffice.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
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

        CustomerResponse.CustomerInfo response = customerService.findOneCustomer(customerId);

        assertEquals("홍길동", response.name());
        assertEquals("asdf@naver.com", response.email());
        assertEquals("010-0000-0000", response.phone());
        assertEquals(AccountStatus.ACTIVE, response.status());
    }

    @Test
    @DisplayName("모든 고객 상세 정보 반환")
    void findAllCustomer_Success() {
        Customer customer1 = (new Customer("홍길동","asdf@naver.com","010-0000-0000", AccountStatus.ACTIVE));
        Customer customer2 = (new Customer("홍길서","asdf2@naver.com","010-1111-1111",AccountStatus.ACTIVE));
        Customer customer3 = (new Customer("홍갈동","asdf3@naver.com","010-2222-1111",AccountStatus.ACTIVE));
        Customer customer4 = (new Customer("홍길남","asdf4@naver.com","010-2222-2222",AccountStatus.INACTIVE));

        List<Customer> customerList = new ArrayList<>();

        customerList.add(customer4);

        Pageable pageable = PageRequest.of(0, 10);

        Page<Customer> mockPage = new PageImpl<>(customerList, pageable, customerList.size());

        given(customerRepository.findAllByNameContainingAndStatus("남", pageable, AccountStatus.INACTIVE)).willReturn(mockPage);

        CustomerResponse.ListCustomerResponse response = customerService.findAllCustomer("남", pageable, AccountStatus.INACTIVE);

        assertEquals("홍길남", response.data().getFirst().name());
    }

    @Test
    @DisplayName("존재하지 않는 ID로 조회 시 예외가 발생해야 한다")
    void findCustomer_Failure() {
        given(customerRepository.findById(anyLong())).willReturn(Optional.empty());

        // IllegalStateException = 999번 고객은 없다고 생각하고 찾아봤을때 뜨는 에러
        assertThrows(IllegalStateException.class,
                () -> customerService.findOneCustomer(999L));
    }
}