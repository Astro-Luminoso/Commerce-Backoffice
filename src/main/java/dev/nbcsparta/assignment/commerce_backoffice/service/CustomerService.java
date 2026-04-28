package dev.nbcsparta.assignment.commerce_backoffice.service;

import dev.nbcsparta.assignment.commerce_backoffice.dto.*;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Customer;
import dev.nbcsparta.assignment.commerce_backoffice.exception.AlreadyDeletedUserException;
import dev.nbcsparta.assignment.commerce_backoffice.exception.ConflictUserException;
import dev.nbcsparta.assignment.commerce_backoffice.exception.CustomerNotFoundException;
import dev.nbcsparta.assignment.commerce_backoffice.repository.CustomerRepository;
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

    public Customer validateCustomer(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
    }

    @Transactional
    public CustomerDetail createCustomer(CreateCustomerRequest request) {
        Customer customer = Customer.from(request);
        customerRepository.save(customer);

        return CustomerDetail.from(customer);
    }

    /**
     * DB에서 필터링 된 고객 정보 DTO에 담아 반환해준다.
     *
     * @param filter   필터링에 필요한 데이터들이 들어이는 객체
     * @param pageable 페이징 정보
     * @return 필터링 된 고객 정보 DTO 리스트를 담은 DTO
     */
    @Transactional(readOnly = true)
    public CustomerListDetail findAllCustomer(PageFilter filter, Pageable pageable) {
        Page<Customer> customerPage = customerRepository.findAllByFilters(filter, pageable);

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
    public CustomerDetail updateDetail(Long customerId, UpdateMyProfileRequest request) {
        Customer customer = validateCustomer(customerId);

        // 수정 요청 이메일과 현재 이메일이 같이 않다면 DB 접근
        if (!customer.getEmail().equals(request.email())) {
            if (customerRepository.existsByEmail(request.email())) {
                throw new ConflictUserException("이미 존재하는 사용자입니다.");
            }
        }

        customer.updateProfile(request);

        return CustomerDetail.from(customer);
    }

    @Transactional
    public CustomerStatusResponse updateStatus(Long customerId, UpdateCustomerStatusRequest request) {
        Customer customer = validateCustomer(customerId);
        customer.updateStatus(request.status());

        return CustomerStatusResponse.from(customer);
    }

    @Transactional
    public void deleteCustomer(Long customerId) {
        Customer customer = validateCustomer(customerId);

        if (customer.isDeleted())
            throw new AlreadyDeletedUserException("이미 삭제 상태입니다.");

        customer.setAccountDeletion();
    }
}
