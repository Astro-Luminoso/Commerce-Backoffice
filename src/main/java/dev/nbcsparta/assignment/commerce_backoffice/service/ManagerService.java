package dev.nbcsparta.assignment.commerce_backoffice.service;

import dev.nbcsparta.assignment.commerce_backoffice.dto.*;
import dev.nbcsparta.assignment.commerce_backoffice.dto.manager.ManagerDetail;
import dev.nbcsparta.assignment.commerce_backoffice.dto.manager.ManagerListDetail;
import dev.nbcsparta.assignment.commerce_backoffice.dto.manager.ManagerRoleUpdate;
import dev.nbcsparta.assignment.commerce_backoffice.dto.manager.ManagerStatusUpdate;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Authority;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Manager;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.Role;
import dev.nbcsparta.assignment.commerce_backoffice.exception.ManagerNotFoundException;
import dev.nbcsparta.assignment.commerce_backoffice.exception.NullValueException;
import dev.nbcsparta.assignment.commerce_backoffice.repository.ManagerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final AuthorityService authorityService;
    private final AuthorityManagementService authManagementService;

    public ManagerService(
            ManagerRepository managerRepository,
            AuthorityService authorityService,
            AuthorityManagementService authManagementService
            ) {
        this.managerRepository = managerRepository;
        this.authorityService = authorityService;
        this.authManagementService = authManagementService;
    }


    /**
     * 관리자 전체 조회 서비스.
     * 쿼리 파라미터로 name과 email, 페이지번호, 페이지당 개수(기본값 10) 정렬기준 정렬 순서를 받고 역할 및 상태값으로 필터링하여 관리자 목록을 반환한다.
     * pageable 파라미터를 제외한 나머지 값은 null 일 수 있다.
     *
     * @param name 관리자 이름
     * @param email 관리자 이메일
     * @param role 관리자 권한
     * @param status 관리자 계정 상태
     * @param pageable 응답에 필요한 페이지네이션 정보
     * @return 관리자 상세정보 ManagerDetail이 Page<T> 형태로 담긴 DTO ManagerListDetail로 반환
     */
    @Transactional(readOnly = true)
    public ManagerListDetail listAllManager(
            String name,
            String email,
            Role role,
            AccountStatus status,
            Pageable pageable
    ) {
        Page<Manager> managerList = managerRepository.findAll(name, email, role, status, pageable);

        return ManagerListDetail.from(managerList);
    }

    /**
     * 관리자 단건 조회 서비스
     *
     * @param managerId 관리자 고유 번호
     * @return 관리자 상세정보 DTO ManagerDetail로 반환
     */
    @Transactional(readOnly = true)
    public ManagerDetail findOneManager(Long managerId) {
        Manager manager = managerRepository.findById(managerId).orElseThrow(ManagerNotFoundException::new);

        return ManagerDetail.from(manager);
    }

    /**
     * 관리자 계정 상태 업데이트 서비스
     *
     * @param managerId 업데이트할 관리자 고유 번호
     * @param reqBody 업데이트정보가 담긴 DTO ManagerStatusUpdate
     */
    public void updateManagerStatus(Long managerId, ManagerStatusUpdate reqBody) {
        // 거부시에는 승인일 때와 달리 이유를 입력해야 합니다.
        if (reqBody.status() == AccountStatus.DENIED && reqBody.reason() == null) {
            throw new NullValueException();
        }

        Manager manager = managerRepository.findById(managerId).orElseThrow(ManagerNotFoundException::new);
        manager.updateStatus(reqBody);

        List<Authority> authorities = authorityService.getAuthoritiesByRole(manager.getRole());
        authManagementService.grantAuthorities(manager, authorities);
    }

    public void updateManagerRole(Long managerId, ManagerRoleUpdate reqBody) {
        Manager manager = managerRepository.findById(managerId).orElseThrow(ManagerNotFoundException::new);
        manager.updateRole(reqBody);
    }

    public ManagerDetail updateManagerDetail(Long managerId, UpdateMyProfileRequest reqBody) {
        Manager manager = managerRepository.findById(managerId).orElseThrow(ManagerNotFoundException::new);
        manager.updateProfile(reqBody);

        return ManagerDetail.from(manager);
    }

    public void deleteManager(Long managerId) {
        Manager manager = managerRepository. findById(managerId).orElseThrow(ManagerNotFoundException::new);
        manager.setAccountDeletion();
        manager.updateStatus(AccountStatus.INACTIVE);
    }
}
