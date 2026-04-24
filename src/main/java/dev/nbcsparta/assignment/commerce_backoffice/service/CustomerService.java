package dev.nbcsparta.assignment.commerce_backoffice.service;

import dev.nbcsparta.assignment.commerce_backoffice.dto.CustomerResponse;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Customer;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;
import dev.nbcsparta.assignment.commerce_backoffice.exception.CustomerNotFoundException;
import dev.nbcsparta.assignment.commerce_backoffice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * DB에서 필터링 된 고객 정보 DTO에 담아 반환해준다.
     *
     * @param keyword  고객 이름 필터용
     * @param email    고객 이메일 필터용
     * @param pageable 페이징 정보
     * @param status   고객 상태 필터용
     * @return 필터링 된 고객 정보 DTO 리스트를 담은 DTO
     */
    @Transactional(readOnly = true)
    public CustomerResponse.ListCustomerResponse findAllCustomer(
            String keyword, String email, Pageable pageable, AccountStatus status
    ) {
        Page<Customer> customerPage = customerRepository.findAllByFilters(keyword, email, pageable, status);

        return CustomerResponse.ListCustomerResponse.from(customerPage);
    }

    /**
     * 고유 번호에 해당하는 고객 정보를 찾아 반환
     *
     * @param customerId 고객 고유 번호
     * @return 찾은 고객 정보 DTO
     */
    @Transactional(readOnly = true)
    public CustomerResponse.CustomerInfo findOneCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new CustomerNotFoundException("존재하지 않는 고객입니다.")
        );

        return CustomerResponse.CustomerInfo.from(customer);
    }
}
