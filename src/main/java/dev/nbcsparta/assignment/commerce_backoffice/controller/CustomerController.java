package dev.nbcsparta.assignment.commerce_backoffice.controller;

import dev.nbcsparta.assignment.commerce_backoffice.dto.CustomerInfo;
import dev.nbcsparta.assignment.commerce_backoffice.dto.ListCustomerResponse;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;
import dev.nbcsparta.assignment.commerce_backoffice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
     * @param name     이름 검색시 필요한 단어
     * @param pageable 이메일 검색시 필요한 정보
     * @param status   상태 필터링 시 필요한 정보
     * @return 필터링된 고객의 정보, 페이징 정보
     */
    @GetMapping()
    public ResponseEntity<ListCustomerResponse> getAllCustomer(
            @RequestParam(required = false)
            String name,

            @RequestParam(required = false)
            String email,

            @PageableDefault(sort = "name")
            Pageable pageable,

            @RequestParam(required = false)
            String status
    ) {
        AccountStatus requestStatus = AccountStatus.getEnum(status);

        // 입력받은 페이지 넘버가 1이라면 백엔드 인덱스에서는 0을 검색해야 하기때문에 -1을 해줍니다.
        int fixedPageNumber = Math.max(0, pageable.getPageNumber() - 1);

        // 페이지 넘버를 -1 한 넘버를 가진 Pageable 생성
        Pageable customPageable = PageRequest.of(
                fixedPageNumber,
                pageable.getPageSize(),
                pageable.getSort()
        );

        ListCustomerResponse customerResponse =
                customerService.findAllCustomer(name, email, customPageable, requestStatus);

        return ResponseEntity.status(HttpStatus.OK).body(customerResponse);
    }

    /**
     * 고유 번호에 해당하는 고객 정보 조회
     *
     * @param customerId 조회하고 싶은 고객 고유 번호
     * @return 조회한 고객 정보
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerInfo> getOneCustomer(
            @PathVariable Long customerId
    ) {
        CustomerInfo customerResponse =
                customerService.findOneCustomer(customerId);

        return ResponseEntity.status(HttpStatus.OK).body(customerResponse);
    }
}
