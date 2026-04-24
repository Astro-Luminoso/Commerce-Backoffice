package dev.nbcsparta.assignment.commerce_backoffice.controller;

import dev.nbcsparta.assignment.commerce_backoffice.dto.CustomerResponse;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;
import dev.nbcsparta.assignment.commerce_backoffice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping()
    public ResponseEntity<CustomerResponse.ListCustomerResponse> getAllCustomer(
            @RequestParam(required = false) String keyword,
            @PageableDefault(
                    sort = "id", direction = Sort.Direction.DESC
            ) Pageable pageable,
            @RequestParam(required = false) AccountStatus status
    ) {
        CustomerResponse.ListCustomerResponse responsePage =
                customerService.findAllCustomer(keyword, pageable, status);

        return new ResponseEntity<>(responsePage, HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse.CustomerInfo> getOneCustomer(
            @PathVariable Long customerId
    ) {
        CustomerResponse.CustomerInfo customerResponse =
                customerService.findOneCustomer(customerId);

        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }
}
