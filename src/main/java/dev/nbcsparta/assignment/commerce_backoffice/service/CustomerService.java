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

    /**
     * ID에 해당하는 고객을 찾아서 반환해줍니다.
     *
     * @param customerId 찾을 고객 고유 번호
     * @return 찾은 고객 엔티티
     */
    @Transactional(readOnly = true)
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
    }

    /**
     * 고객을 생성하고 DB에 저장해줍니다.
     *
     * @param request 생성할 고객의 정보
     * @return 생성하고 DB에 저장이된
     */
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
    public CustomerListDetail findAllCustomer(GetCustomerPageFilter filter, Pageable pageable) {
        Page<CustomerDetail> customerPage = customerRepository.findAllCustomerByFilters(filter, pageable);

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
        return customerRepository.findCustomerDetail(customerId).orElseThrow(CustomerNotFoundException::new);
    }

    /**
     * 고객의 정보를 수정합니다.
     *
     * @param customerId 수정할 고객의 고유 번호
     * @param request    수정할 고객의 정보들이 담긴 객체
     * @return 수정된 고객의 정보가 담긴 객체
     */
    @Transactional
    public CustomerDetail updateDetail(Long customerId, UpdateMyProfileRequest request) {
        // 존재하는 고객인지 검사해줍니다.
        Customer customer = getCustomerById(customerId);

        // 수정할 이메일과 현재 이메일이 같지 않다면 DB 접근
        if (!customer.getEmail().equals(request.email())) {
            if (customerRepository.existsByEmail(request.email())) {
                throw new ConflictUserException("이미 존재하는 사용자입니다.");
            }
        }

        customer.updateProfile(request);

        return CustomerDetail.from(customer);
    }

    /**
     * 고객의 상태를 수정해줍니다.
     *
     * @param customerId 수정할 고객의 고유 번호
     * @param request    수정할 상태
     * @return 수정된 고객의 ID와 상태가 담긴 객체
     */
    @Transactional
    public CustomerStatusResponse updateStatus(Long customerId, UpdateCustomerStatusRequest request) {
        Customer customer = getCustomerById(customerId);
        customer.updateStatus(request.status());

        return CustomerStatusResponse.from(customer);
    }

    /**
     * 고객의 삭제 여부를 변경합니다.
     *
     * @param customerId 삭제 상태로 만들 고객 고유 번호
     */
    @Transactional
    public void deleteCustomer(Long customerId) {
        Customer customer = getCustomerById(customerId);

        if (customer.isDeleted())
            throw new AlreadyDeletedUserException("이미 삭제 상태입니다.");

        customer.setAccountDeletion();
    }
}
