package dev.nbcsparta.assignment.commerce_backoffice.repository;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Customer;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("검색 조건, 페이징을 이용한 다수 고객 목록 조회 성공")
    void findAllCustomer_Success() {
        customerRepository.save(new Customer("홍길동","asdf@naver.com","010-0000-0000", AccountStatus.ACTIVE));
        customerRepository.save(new Customer("홍길서","asdf2@naver.com","010-1111-1111",AccountStatus.ACTIVE));
        customerRepository.save(new Customer("홍갈동","asdf3@naver.com","010-2222-1111",AccountStatus.ACTIVE));
        customerRepository.save(new Customer("홍길남","asdf4@naver.com","010-2222-2222",AccountStatus.INACTIVE));

        Pageable pageable = PageRequest.of(0, 10);
        Page<Customer> result = customerRepository.findAllByNameContainingAndStatus("남", pageable, AccountStatus.INACTIVE);

        System.out.println(result.getContent().getFirst().getName());

        assertEquals(1, result.getContent().size());
        assertEquals("홍길남", result.getContent().getFirst().getName());
    }
}