package dev.nbcsparta.assignment.commerce_backoffice.controller;

import dev.nbcsparta.assignment.commerce_backoffice.dto.ManagerList;
import dev.nbcsparta.assignment.commerce_backoffice.dto.SessionManager;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.Role;
import dev.nbcsparta.assignment.commerce_backoffice.service.ManagerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/managers")
public class ManagerController {

    private final ManagerService managerService;

    private final Logger logger = LoggerFactory.getLogger(ManagerController.class);;

    public ManagerController(ManagerService managerRepository) {
        this.managerService = managerRepository;
    }

    /**
     * 관리자 전체 조회 API
     * 쿼리 파라미터로 name과 email, 페이지번호, 페이지당 개수(기본값 10) 정렬기준 정렬 순서를 받고 역할 및 상태값으로 필터링하여 관리자 목록을 반환한다
     *
     * @param name
     * @param email
     * @param pageable
     * @param sessionManager
     * @return
     */
    @GetMapping
    public ResponseEntity<ManagerList> listAllManager(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "registrationDate") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String orderBy,
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) AccountStatus status,
            @SessionAttribute(name = "LOGIN_MANAGER") SessionManager sessionManager) {

        logger.info("GET /managers: Get all managers");
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.fromString(orderBy), sortBy));
        ManagerList managerList = managerService.listAllManager(name, email, role, status, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(managerList);
    }
}
