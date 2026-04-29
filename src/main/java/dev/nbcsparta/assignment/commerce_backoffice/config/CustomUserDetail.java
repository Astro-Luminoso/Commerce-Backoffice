package dev.nbcsparta.assignment.commerce_backoffice.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetail extends User {

    private final Long managerId;

    public CustomUserDetail(
            String username,
            Long managerId,
            Collection<? extends GrantedAuthority> authorities
    ) {
        super(username, "", authorities);
        this.managerId = managerId;
    }

    public long getManagerId() {
        return this.managerId;
    }
}
