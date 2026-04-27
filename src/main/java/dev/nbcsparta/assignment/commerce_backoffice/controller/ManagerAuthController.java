package dev.nbcsparta.assignment.commerce_backoffice.controller;

import dev.nbcsparta.assignment.commerce_backoffice.config.Authentication;
import dev.nbcsparta.assignment.commerce_backoffice.dto.manager.CreateManagerRequest;
import dev.nbcsparta.assignment.commerce_backoffice.dto.manager.CreateManagerResponse;
import dev.nbcsparta.assignment.commerce_backoffice.dto.LoginRequest;
import dev.nbcsparta.assignment.commerce_backoffice.dto.SessionManager;
import dev.nbcsparta.assignment.commerce_backoffice.service.ManagerAuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManagerAuthController {

    private final ManagerAuthService managerAuthService;
    private final Authentication authentication;

    public ManagerAuthController(ManagerAuthService managerAuthService,
                                 Authentication authentication) {
        this.managerAuthService = managerAuthService;
        this.authentication = authentication;
    }

    @PostMapping("/register")
    public ResponseEntity<CreateManagerResponse> register(@Valid @RequestBody CreateManagerRequest req) {
        CreateManagerResponse res = managerAuthService.register(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest req) {
        SessionManager sessionManager = managerAuthService.login(req);
        authentication.login(sessionManager);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse res) {
        authentication.logout();

        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        res.addCookie(cookie);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
