package dev.nbcsparta.assignment.commerce_backoffice.service;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Authority;
import dev.nbcsparta.assignment.commerce_backoffice.entity.HasAuthority;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Manager;
import dev.nbcsparta.assignment.commerce_backoffice.repository.AuthorityManagementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AuthorityManagementService {

    public final AuthorityManagementRepository repository;

    public AuthorityManagementService(AuthorityManagementRepository repository) {
        this.repository = repository;
    }

    public void grantAuthorities(Manager manager, List<Authority> authorities) {
        List<HasAuthority> hasAuthorities = authorities.stream()
                .map(authority -> new HasAuthority(manager, authority))
                .toList();

        if (manager.isActive()) {
            repository.saveAll(hasAuthorities);
        }
    }

    public void removeAllAuthorities(Manager manager) {
        repository.removeAllByManager(manager);
    }
}
