package dev.nbcsparta.assignment.commerce_backoffice.config;

import dev.nbcsparta.assignment.commerce_backoffice.dto.SessionManager;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.Role;
import dev.nbcsparta.assignment.commerce_backoffice.exception.AlreadyLoginException;
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

    public void login(SessionManager sessionManager) {
        if (session.getAttribute("LOGIN_MANAGER") != null) {
            throw new AlreadyLoginException("이미 로그인된 상태입니다.");
        }
        session.setAttribute("LOGIN_MANAGER", sessionManager);
        session.setMaxInactiveInterval(60 * 60 * 24);
    }

    public void logout() {
        session.invalidate();
    }
}
