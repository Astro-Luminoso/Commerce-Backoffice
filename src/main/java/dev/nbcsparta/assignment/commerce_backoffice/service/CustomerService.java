package dev.nbcsparta.assignment.commerce_backoffice.service;

import dev.nbcsparta.assignment.commerce_backoffice.dto.*;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Customer;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;
import dev.nbcsparta.assignment.commerce_backoffice.exception.CustomerNotFoundException;
import dev.nbcsparta.assignment.commerce_backoffice.repository.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Customer validateCustomer(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
    }

    /**
     * DB에서 필터링 된 고객 정보 DTO에 담아 반환해준다.
     *
     * @param name     고객 이름 필터용
     * @param email    고객 이메일 필터용
     * @param pageable 페이징 정보
     * @param status   고객 상태 필터용
     * @return 필터링 된 고객 정보 DTO 리스트를 담은 DTO
     */
    @Transactional(readOnly = true)
    public CustomerListDetail findAllCustomer(
            String name, String email, Pageable pageable, AccountStatus status
    ) {
        Page<Customer> customerPage = customerRepository.findAllByFilters(name, email, pageable, status);

        return CustomerListDetail.from(customerPage);
    }

    /**
     * 고유 번호에 해당하는 고객 정보를 찾아 반환
     *
     * @param customerId 고객 고유 번호
     * @return 찾은 고객 정보 DTO
     */
    @Transactional(readOnly = true)
    public CustomerDetail findOneCustomer(Long customerId) {
        Customer customer = validateCustomer(customerId);

        return CustomerDetail.from(customer);
    }

    @Transactional
    public CustomerDetail updateDetail(Long customerId, UpdateCustomerDetailRequest request) {
        Customer customer = validateCustomer(customerId);

        customer.updateCustomerDetail(request.name(), request.email(), request.phoneNumber());

        return CustomerDetail.from(customer);
    }

    @Transactional
    public CustomerStatusResponse updateStatus(Long customerId, @Valid UpdateCustomerStatusRequest request) {
        Customer customer = validateCustomer(customerId);

        AccountStatus status = AccountStatus.getEnum(request.status());

        customer.updateCustomerStatus(status);

        return CustomerStatusResponse.from(customer);
    }
}
