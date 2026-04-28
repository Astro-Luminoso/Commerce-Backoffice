package dev.nbcsparta.assignment.commerce_backoffice.service;

import dev.nbcsparta.assignment.commerce_backoffice.dto.*;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Customer;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;
import dev.nbcsparta.assignment.commerce_backoffice.exception.AleadyDeletedUserException;
import dev.nbcsparta.assignment.commerce_backoffice.exception.ConflictUserException;
import dev.nbcsparta.assignment.commerce_backoffice.exception.CustomerNotFoundException;
import dev.nbcsparta.assignment.commerce_backoffice.repository.CustomerRepository;
import dev.nbcsparta.assignment.commerce_backoffice.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public CustomerService(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Customer validateCustomer(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
    }

    @Transactional
    public CustomerDetail createCustomer(CreateCustomerRequest request) {
        Customer customer = new Customer(request.name(),request.email(), request.phoneNumber(), request.status());
        Customer savedCustomer = customerRepository.save(customer);

        return CustomerDetail.from(savedCustomer);
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
        // orderRepository.findByCustomerId()
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

        boolean isExistEmail = customerRepository.existsByEmail(request.email());

        if (isExistEmail) {
            throw new ConflictUserException("이미 존재하는 사용자입니다.");
        }

        customer.updateProfile(request);

        return CustomerDetail.from(customer);
    }

    @Transactional
    public CustomerStatusResponse updateStatus(Long customerId, UpdateCustomerStatusRequest request) {
        Customer customer = validateCustomer(customerId);

        AccountStatus status = request.status();

        customer.updateStatus(status);

        return CustomerStatusResponse.from(customer);
    }

    @Transactional
    public void deleteCustomer(Long customerId) {
        Customer customer = validateCustomer(customerId);

        if (customer.isDeleted())
            throw new AleadyDeletedUserException("이미 삭제 상태입니다.");

        customer.setAccountDeletion();
    }
}
