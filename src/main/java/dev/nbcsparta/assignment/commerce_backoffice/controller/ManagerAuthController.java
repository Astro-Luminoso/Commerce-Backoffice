package dev.nbcsparta.assignment.commerce_backoffice.controller;

import dev.nbcsparta.assignment.commerce_backoffice.config.Authentication;
import dev.nbcsparta.assignment.commerce_backoffice.dto.*;
import dev.nbcsparta.assignment.commerce_backoffice.service.ManagerAuthService;
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
    public ResponseEntity<CommonResponse<CreateManagerResponse>> register(
            @Valid @RequestBody CreateManagerRequest req
    ) {
        CreateManagerResponse res = managerAuthService.register(req);

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.success(HttpStatus.CREATED, "가입 완료", res));
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<Void>> login(
            @Valid @RequestBody LoginRequest req
    ) {
        SessionManager sessionManager = managerAuthService.login(req);
        authentication.login(sessionManager);

        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.success(HttpStatus.OK, "로그인 성공"));
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponse<Void>> logout(HttpServletResponse res) {
        authentication.logout();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
