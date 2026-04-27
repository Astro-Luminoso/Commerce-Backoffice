package dev.nbcsparta.assignment.commerce_backoffice.config;

import dev.nbcsparta.assignment.commerce_backoffice.dto.SessionManager;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.Role;
import dev.nbcsparta.assignment.commerce_backoffice.exception.AccessForbiddenException;
import dev.nbcsparta.assignment.commerce_backoffice.exception.AlreadyLoginException;
import dev.nbcsparta.assignment.commerce_backoffice.exception.UnauthorizedException;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class Authentication {

    private final HttpSession session;

    public Authentication(HttpSession session) {
        this.session = session;
    }

    public void hasAuthority(Role requiredRole) {
        SessionManager manager = (SessionManager) session.getAttribute("LOGIN_MANAGER");
        if (manager.role().getAccessLevel() < requiredRole.getAccessLevel()) {
            throw new AccessForbiddenException();
        }
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

    /**
     * 현재 로그인된 관리자의 세션 정보를 반환
     * @return 현재 로그인된 {@link SessionManager} 정보
     * @throws UnauthorizedException 로그인되어 있지 않은 경우 (세션 없음)
     */
    public SessionManager getCurrentManager() {
        SessionManager manager = (SessionManager) session.getAttribute("LOGIN_MANAGER");
        if (manager == null) {
            throw new UnauthorizedException("로그인이 되어 있지 않습니다.");
        }
        return manager;
    }
}
