package dev.nbcsparta.assignment.commerce_backoffice.config;

import dev.nbcsparta.assignment.commerce_backoffice.dto.SessionManager;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.Role;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class Authentication {

    private final HttpSession session;

    public Authentication(HttpSession session) {
        this.session = session;
    }

    public boolean hasAuthority(Role requiredRole) {
        SessionManager manager = (SessionManager) session.getAttribute("LOGIN_MANAGER");
        return manager.role().getAccessLevel() >= requiredRole.getAccessLevel();
    }
}
