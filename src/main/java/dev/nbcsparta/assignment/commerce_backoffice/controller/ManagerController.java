package dev.nbcsparta.assignment.commerce_backoffice.controller;

import dev.nbcsparta.assignment.commerce_backoffice.config.Authentication;
import dev.nbcsparta.assignment.commerce_backoffice.dto.*;
import dev.nbcsparta.assignment.commerce_backoffice.dto.manager.ManagerDetail;
import dev.nbcsparta.assignment.commerce_backoffice.dto.manager.ManagerListDetail;
import dev.nbcsparta.assignment.commerce_backoffice.dto.manager.ManagerRoleUpdate;
import dev.nbcsparta.assignment.commerce_backoffice.dto.manager.ManagerStatusUpdate;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.Role;
import dev.nbcsparta.assignment.commerce_backoffice.service.ManagerService;

import jakarta.validation.Valid;
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
    private final Authentication authentication;

    private final Logger logger = LoggerFactory.getLogger(ManagerController.class);

    public ManagerController(ManagerService managerRepository, Authentication authentication) {
        this.managerService = managerRepository;
        this.authentication = authentication;
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
     * @return 응답 엔티티에 관리자 목록과 HTTP 상태 코드 200 반환
     */
    @GetMapping
    public ResponseEntity<ManagerListDetail> responseManagerListDetail(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "registrationDate") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String orderBy,
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) AccountStatus status
    ) {
        logger.info("GET /managers: Get all managers");
        authentication.checkAuthority(Role.SUPER);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.fromString(orderBy), sortBy));
        ManagerListDetail managerList = managerService.listAllManager(name, email, role, status, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(managerList);
    }

    /**
     * 관리자 고유번호를 이용한 관리자 상세 조회
     *
     * @param id 관리자 고유번호 path 값으로 가져옴
     * @return 응답 엔티티에 관리자 상세 정보와 HTTP 상태 코드 200 반환
     */
    @GetMapping("/{id}")
    public ResponseEntity<ManagerDetail> responseManagerDetail(
            @PathVariable Long id
    ){
        logger.info("GET /managers/{}: Get manager detail", id);
        authentication.checkAuthority(Role.SUPER);
        ManagerDetail managerDetail = managerService.findOneManager(id);

        return ResponseEntity.status(HttpStatus.OK).body(managerDetail);
    }

    /**
     * 관리자 고유번호를 이용한 관리자 계정 상태 업데이트
     *
     * @param managerId 메니저 고유번호 path 값으로 가져옴
     * @param reqBody 상태 업데이트에 필요한 정보가 담긴 DTO ManagerStatusUpdate 형태로 요청 바디에서 가져옴
     * @return 응답 엔티티에 HTTP 상태 코드 200 반환
     */
    @PatchMapping("/{managerId}/status")
    public ResponseEntity<Void> updateManagerStatus(
            @PathVariable Long managerId,
            @Valid @RequestBody ManagerStatusUpdate reqBody) {
        logger.info("PATCH /managers/{}/status: Update manager status", managerId);
        managerService.updateManagerStatus(managerId, reqBody);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 관리자 고유번호를 이용한 관리자 권한 업데이트
     *
     * @param managerId 메니저 고유번호 path 값으로 가져옴
     * @param reqBody 상태 업데이트에 필요한 정보가 담긴 DTO ManagerRoleUpdate 형태로 요청 바디에서 가져옴
     * @return 응답 엔티티에 HTTP 상태 코드 200 반환
     */
    @PatchMapping("/{managerId}/role")
    public ResponseEntity<Void> updateManagerRole(
            @PathVariable Long managerId,
            @Valid @RequestBody ManagerRoleUpdate reqBody
    ) {
        logger.info("PATCH /managers/{}/role: Update manager role", managerId);
        authentication.checkAuthority(Role.SUPER);
        managerService.updateManagerRole(managerId, reqBody);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{managerId}")
    public ResponseEntity<ManagerDetail> updateMangerDetail(
            @PathVariable Long managerId,
            @RequestBody UpdateMyProfileRequest reqBody
    ) {
        logger.info("PUT /managers/{}: Update manager detail", managerId);
        authentication.checkAuthority(Role.SUPER);
        ManagerDetail managerDetail = managerService.updateManagerDetail(managerId, reqBody);

        return ResponseEntity.status(HttpStatus.OK).body(managerDetail);
    }

    @DeleteMapping("/{managerId}")
    public ResponseEntity<Void> deleteManager(
            @PathVariable Long managerId
    ) {
        logger.info("DELETE /managers/{}: Delete manager", managerId);
        authentication.checkAuthority(Role.SUPER);
        managerService.deleteManager(managerId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}