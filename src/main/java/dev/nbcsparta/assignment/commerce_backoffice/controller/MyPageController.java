package dev.nbcsparta.assignment.commerce_backoffice.controller;

import dev.nbcsparta.assignment.commerce_backoffice.config.Authentication;
import dev.nbcsparta.assignment.commerce_backoffice.dto.MyProfileResponse;
import dev.nbcsparta.assignment.commerce_backoffice.dto.SessionManager;
import dev.nbcsparta.assignment.commerce_backoffice.dto.UpdateMyPasswordRequest;
import dev.nbcsparta.assignment.commerce_backoffice.dto.UpdateMyProfileRequest;
import dev.nbcsparta.assignment.commerce_backoffice.service.MyPageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/managers/mypages")
public class MyPageController {

    private final MyPageService myPageService;
    private final Authentication authentication;

    public MyPageController(MyPageService myPageService, Authentication authentication) {
        this.myPageService = myPageService;
        this.authentication = authentication;
    }

    @GetMapping
    public ResponseEntity<MyProfileResponse> getMyProfile() {
        SessionManager sessionManager = authentication.getCurrentManager();
        MyProfileResponse res = myPageService.getMyProfile(sessionManager.id());
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PutMapping
    public ResponseEntity<MyProfileResponse> updateMyProfile(@Valid @RequestBody UpdateMyProfileRequest req) {
        SessionManager sessionManager = authentication.getCurrentManager();
        MyProfileResponse res = myPageService.updateMyProfile(sessionManager.id(), req);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PatchMapping
    public ResponseEntity<Void> updateMyPassword(@Valid @RequestBody UpdateMyPasswordRequest req) {
        SessionManager sessionManager = authentication.getCurrentManager();
        myPageService.updateMyPassword(sessionManager.id(), req);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
