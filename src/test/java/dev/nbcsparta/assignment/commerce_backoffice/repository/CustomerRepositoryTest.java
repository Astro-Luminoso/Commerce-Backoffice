package dev.nbcsparta.assignment.commerce_backoffice.repository;

import dev.nbcsparta.assignment.commerce_backoffice.dto.customer.CreateCustomerRequest;
import dev.nbcsparta.assignment.commerce_backoffice.dto.customer.CustomerDetail;
import dev.nbcsparta.assignment.commerce_backoffice.dto.customer.GetCustomerPageFilter;
import dev.nbcsparta.assignment.commerce_backoffice.dto.UpdateMyProfileRequest;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Customer;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;
import dev.nbcsparta.assignment.commerce_backoffice.service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    @DisplayName("검색 조건, 페이징을 이용한 다수 고객 목록 조회 성공")
    void findAllCustomer_Success() {

        CreateCustomerRequest createCustomerRequest1 = new CreateCustomerRequest(
                "홍길동",
                "asdf1@naver.com",
                "010-0000-0000"
        );

        CreateCustomerRequest createCustomerRequest2 = new CreateCustomerRequest(
                "홍길서",
                "asdf2@naver.com",
                "010-1111-1111"
        );

        CreateCustomerRequest createCustomerRequest3 = new CreateCustomerRequest(
                "홍갈동",
                "asdf3@naver.com",
                "010-2222-1111"
        );

        CreateCustomerRequest createCustomerRequest4 = new CreateCustomerRequest(
                "홍길남",
                "asdf4@naver.com",
                "010-2222-2222"
        );

        customerRepository.save(Customer.from(createCustomerRequest1));
        customerRepository.save(Customer.from(createCustomerRequest2));
        customerRepository.save(Customer.from(createCustomerRequest3));
        customerRepository.save(Customer.from(createCustomerRequest4));

        Pageable pageable = PageRequest.of(0, 3);
        // 이메일에 4가 들어가는 사람 필터링
        GetCustomerPageFilter filter = new GetCustomerPageFilter(null, "4", AccountStatus.ACTIVE);
        Page<CustomerDetail> result = customerRepository.findAllCustomerByFilters(filter, pageable);
        // 1사람만 나옴, 이름은 홍길남
        assertEquals(1, result.getContent().size());
        assertEquals("홍길남", result.getContent().getFirst().name());
    }

    @Test
    @DisplayName("검색 조건, 페이징을 이용한 다수 고객 목록 조회 실패")
    void findAllCustomer_Failure() {
        CreateCustomerRequest createCustomerRequest1 = new CreateCustomerRequest(
                "홍길동",
                "asdf1@naver.com",
                "010-0000-0000"
        );

        CreateCustomerRequest createCustomerRequest2 = new CreateCustomerRequest(
                "홍길서",
                "asdf2@naver.com",
                "010-1111-1111"
        );

        CreateCustomerRequest createCustomerRequest3 = new CreateCustomerRequest(
                "홍갈동",
                "asdf3@naver.com",
                "010-2222-1111"
        );

        CreateCustomerRequest createCustomerRequest4 = new CreateCustomerRequest(
                "홍길남",
                "asdf4@naver.com",
                "010-2222-2222"
        );

        customerRepository.save(Customer.from(createCustomerRequest1));
        customerRepository.save(Customer.from(createCustomerRequest2));
        customerRepository.save(Customer.from(createCustomerRequest3));
        customerRepository.save(Customer.from(createCustomerRequest4));

        Pageable pageable = PageRequest.of(0, 10);
        GetCustomerPageFilter filter = new GetCustomerPageFilter(null, "3", AccountStatus.INACTIVE);
        Page<CustomerDetail> result = customerRepository.findAllCustomerByFilters(filter, pageable);

        assertEquals("홍갈동", result.getContent().getFirst().name());
    }

    @Test
    @DisplayName("고객 이메일 수정 실패 (중복)")
    void updateCustomerEmail_Failure() {
        Long customerId = 1L;

        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest(
                "홍길동",
                "asdf1@naver.com",
                "010-0000-0000"
        );

        Customer customer = Customer.from(createCustomerRequest);

        given(customerRepository.findById(customerId)).willReturn(Optional.of(customer));

        UpdateMyProfileRequest request = new UpdateMyProfileRequest(
                "홍길동",
                "asdf@naver.com",
                "010-0000-0000"
        );

        customerService.updateDetail(customerId, request);

    }
}