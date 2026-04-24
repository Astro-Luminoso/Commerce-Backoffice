package dev.nbcsparta.assignment.commerce_backoffice.controller;

import dev.nbcsparta.assignment.commerce_backoffice.dto.ManagerListDetail;
import dev.nbcsparta.assignment.commerce_backoffice.dto.SessionManager;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.Role;
import dev.nbcsparta.assignment.commerce_backoffice.service.ManagerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/managers")
public class ManagerController {

    private final ManagerService managerService;

    private final Logger logger = LoggerFactory.getLogger(ManagerController.class);

    public ManagerController(ManagerService managerRepository) {
        this.managerService = managerRepository;
    }

    /**
     *
     * 관리자 전체 조회 API
     * 쿼리 파라미터로 name과 email, 페이지번호, 페이지당 개수(기본값 10) 정렬기준 정렬 순서를 받고 역할 및 상태값으로 필터링하여 관리자 목록을 반환한다
     *
     * @param name 관리자명
     * @param email 관리자 이메일
     * @param page 관리자 목록 페이지 번호 (기본값 1)
     * @param size 페이지 사이즈 기본값 10
     * @param sortBy 정렬 기준
     * @param orderBy 정렬 순서 (asc, desc)
     * @param role 관리자 권한
     * @param status 관리자 계정 상태
     * @param sessionManager 세션에서 로그인한 관리자 정보
     * @return 응답 엔티티에 관리자 목록과 HTTP 상태 코드 200 반환
     */
    @GetMapping
    public ResponseEntity<ManagerListDetail> listAllManager(
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
        ManagerListDetail managerList = managerService.listAllManager(name, email, role, status, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(managerList);
    }
}
