package dev.nbcsparta.assignment.commerce_backoffice.controller;

import dev.nbcsparta.assignment.commerce_backoffice.dto.CreateManagerRequest;
import dev.nbcsparta.assignment.commerce_backoffice.dto.CreateManagerResponse;
import dev.nbcsparta.assignment.commerce_backoffice.dto.LoginRequest;
import dev.nbcsparta.assignment.commerce_backoffice.dto.SessionManager;
import dev.nbcsparta.assignment.commerce_backoffice.exception.AlreadyLoginException;
import dev.nbcsparta.assignment.commerce_backoffice.service.ManagerAuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManagerAuthController {

    private final ManagerAuthService managerAuthService;

    public ManagerAuthController(ManagerAuthService managerAuthService) {
        this.managerAuthService = managerAuthService;
    }

    @PostMapping("/register")
    public ResponseEntity<CreateManagerResponse> register(@Valid @RequestBody CreateManagerRequest req) {
        CreateManagerResponse res = managerAuthService.register(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest req, HttpServletRequest httpReq) {
        SessionManager sessionManager = managerAuthService.login(req);

        HttpSession session = httpReq.getSession(false);
        if (session != null && session.getAttribute("LOGIN_MANAGER") != null) {
            throw new AlreadyLoginException("이미 로그인된 상태입니다.");
        }

        session = httpReq.getSession(true);
        session.setAttribute("LOGIN_MANAGER", sessionManager);
        session.setMaxInactiveInterval(60 * 60 * 24);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest req, HttpServletResponse res) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
            Cookie cookie = new Cookie("JSESSIONID", null);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            res.addCookie(cookie);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
