package dev.nbcsparta.assignment.commerce_backoffice.usecase;

import dev.nbcsparta.assignment.commerce_backoffice.config.jwt.BlackListManager;
import dev.nbcsparta.assignment.commerce_backoffice.dto.UpdateMyProfileRequest;
import dev.nbcsparta.assignment.commerce_backoffice.dto.manager.ManagerDetail;
import dev.nbcsparta.assignment.commerce_backoffice.dto.manager.ManagerListDetail;
import dev.nbcsparta.assignment.commerce_backoffice.dto.manager.ManagerRoleUpdate;
import dev.nbcsparta.assignment.commerce_backoffice.dto.manager.ManagerStatusUpdate;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Authority;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Manager;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.Role;
import dev.nbcsparta.assignment.commerce_backoffice.service.AuthorityManagementService;
import dev.nbcsparta.assignment.commerce_backoffice.service.AuthorityService;
import dev.nbcsparta.assignment.commerce_backoffice.service.ManagerService;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SuperRoleAction {

    private final ManagerService managerService;
    private final AuthorityService authorityService;
    private final AuthorityManagementService authorityManagementService;
    private final BlackListManager blackListManager;

    public SuperRoleAction(
            ManagerService managerService,
            AuthorityService authorityService,
            AuthorityManagementService authorityManagementService,
            BlackListManager blackListManager
    ) {
        this.managerService = managerService;
        this.authorityService = authorityService;
        this.authorityManagementService = authorityManagementService;
        this.blackListManager = blackListManager;
    }

    private void updateManagerRole(Manager manager) {
        this.authorityManagementService.removeAllAuthorities(manager);
        List<Authority> authorities = authorityService.getAuthoritiesByRole(manager.getRole());
        authorityManagementService.grantAuthorities(manager, authorities);
        blackListManager.registerManagerId(manager.getId());
    }

    public ManagerListDetail getAllExistManagers(
            @Nullable String name,
            @Nullable String email,
            @Nullable Role role,
            @Nullable AccountStatus status,
            @Nonnull Pageable pageable
    ) {
        return managerService.listAllManager(name, email, role, status, pageable, false);
    }

    public ManagerDetail getExistManagerById(Long managerId) {
        return managerService.findOneManager(managerId);
    }

    public ManagerDetail updateManagerStatus(Long managerId, ManagerStatusUpdate reqBody) {
        Manager manager = managerService.updateManagerStatus(managerId, reqBody);
        this.updateManagerRole(manager);

        return ManagerDetail.from(manager);
    }

    public ManagerDetail updateManagerAuthority(Long managerId, ManagerRoleUpdate reqBody) {
        Manager manager = managerService.updateManagerRole(managerId, reqBody);
        this.updateManagerRole(manager);

        return ManagerDetail.from(manager);
    }

    public ManagerDetail updateManagerDetail(Long managerId, UpdateMyProfileRequest reqBody) {
        Manager manager = managerService.updateManagerDetail(managerId, reqBody);

        return ManagerDetail.from(manager);
    }

    public void deleteManager(Long managerId) {
        Manager manager = managerService.softDelete(managerId);
        authorityManagementService.removeAllAuthorities(manager);
        blackListManager.registerManagerId(manager.getId());
    }
}
