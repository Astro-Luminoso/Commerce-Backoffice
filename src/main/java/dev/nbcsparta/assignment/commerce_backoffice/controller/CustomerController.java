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

    /**
     * 필터에 따라 고객의 전체 정보를 조회합니다.
     *
     * @param keyword  이름 검색시 필요한 단어
     * @param pageable 이메일 검색시 필요한 정보
     * @param status   상태 필터링 시 필요한 정보
     * @return 필터링된 고객의 정보, 페이징 정보
     */
    @GetMapping()
    public ResponseEntity<CustomerResponse.ListCustomerResponse> getAllCustomer(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String email,
            @PageableDefault(
                    sort = "id", direction = Sort.Direction.DESC
            ) Pageable pageable,
            @RequestParam(required = false) String status
    ) {
        AccountStatus requestStatus = AccountStatus.getEnum(status);

        CustomerResponse.ListCustomerResponse customerResponse =
                customerService.findAllCustomer(keyword, email, pageable, requestStatus);

        return ResponseEntity.status(HttpStatus.OK).body(customerResponse);
    }

    /**
     * 고유 번호에 해당하는 고객 정보 조회
     *
     * @param customerId 조회하고 싶은 고객 고유 번호
     * @return 조회한 고객 정보
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse.CustomerInfo> getOneCustomer(
            @PathVariable Long customerId
    ) {
        CustomerResponse.CustomerInfo customerResponse =
                customerService.findOneCustomer(customerId);

        return ResponseEntity.status(HttpStatus.OK).body(customerResponse);
    }
}
