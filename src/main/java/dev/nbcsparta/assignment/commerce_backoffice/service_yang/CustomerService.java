package dev.nbcsparta.assignment.commerce_backoffice.service_yang;

import dev.nbcsparta.assignment.commerce_backoffice.dto_yang.CustomerResponse;
import dev.nbcsparta.assignment.commerce_backoffice.entity_yang.Customer;
import dev.nbcsparta.assignment.commerce_backoffice.enum_yang.CustomerStatus;
import dev.nbcsparta.assignment.commerce_backoffice.repository_yang.CustomerRepository;
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

    @Transactional(readOnly = true)
    public CustomerResponse.ListCustomerResponse findAllCustomer(
            String keyword, Pageable pageable, CustomerStatus status
    ) {
        Page<Customer> customerPage = customerRepository.findAllByFilters(keyword, pageable, status);

        return CustomerResponse.ListCustomerResponse.from(customerPage);
    }

    @Transactional(readOnly = true)
    public CustomerResponse.CustomerInfo findOneCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 고객입니다.")
        );

        return CustomerResponse.CustomerInfo.from(customer);
    }
}
