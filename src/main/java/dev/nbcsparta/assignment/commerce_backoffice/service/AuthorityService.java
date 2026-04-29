package dev.nbcsparta.assignment.commerce_backoffice.service;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Authority;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.Role;
import dev.nbcsparta.assignment.commerce_backoffice.repository.AuthorityRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AuthorityService {

    private final AuthorityRepository authorityRepository;
    private Map<Role, Authority> authorityMap;

    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @PostConstruct
    private void updateAuthoritiesStatus() {
        List<Authority> authorities = authorityRepository.findAll();

        this.authorityMap = authorities.stream()
                .collect(Collectors.toMap(Authority::getRole, authority -> authority));
    }

    public List<Authority> getAuthoritiesByRole(Role role) {
        return authorityMap.entrySet().stream()
                .filter(entry -> entry.getKey().compareTo(role) <= 0)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}
