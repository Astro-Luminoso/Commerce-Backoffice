package dev.nbcsparta.assignment.commerce_backoffice.controller_yang;

import dev.nbcsparta.assignment.commerce_backoffice.dto_yang.CustomerResponse;
import dev.nbcsparta.assignment.commerce_backoffice.enum_yang.CustomerStatus;
import dev.nbcsparta.assignment.commerce_backoffice.service_yang.CustomerService;
import org.hibernate.boot.model.source.spi.IdentifierSourceAggregatedComposite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
                    page = 0, sort = "id", direction = Sort.Direction.DESC
            ) Pageable pageable,
            @RequestParam(required = false) CustomerStatus status
    ) {
        CustomerResponse.ListCustomerResponse responsePage =
                customerService.findAllCustomer(keyword, pageable, status);

        return new ResponseEntity<>(responsePage, HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse.CustomerInfo> getOneCustomer(
            @PathVariable Long customerId
    ) {
        CustomerResponse.CustomerInfo customerResponse = customerService.findOneCustomer(customerId);

        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }
}
