package dev.nbcsparta.assignment.commerce_backoffice.controller;

import dev.nbcsparta.assignment.commerce_backoffice.config.Authentication;
import dev.nbcsparta.assignment.commerce_backoffice.dto.*;
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
    public ResponseEntity<CommonResponse<MyProfileResponse>> getMyProfile() {
        SessionManager sessionManager = authentication.getCurrentManager();
        MyProfileResponse res = myPageService.getMyProfile(sessionManager.id());

        return CommonResponse
                .success(HttpStatus.OK, "프로필 조회 완료", res)
                .toResponseEntity();
    }

    @PutMapping
    public ResponseEntity<CommonResponse<MyProfileResponse>> updateMyProfile(
            @Valid @RequestBody UpdateMyProfileRequest req
    ) {
        SessionManager sessionManager = authentication.getCurrentManager();
        MyProfileResponse res = myPageService.updateMyProfile(sessionManager.id(), req);

        return CommonResponse
                .success(HttpStatus.OK, "프로필 수정 완료", res)
                .toResponseEntity();
    }

    @PatchMapping
    public ResponseEntity<CommonResponse<Void>> updateMyPassword(
            @Valid @RequestBody UpdateMyPasswordRequest req
    ) {
        SessionManager sessionManager = authentication.getCurrentManager();
        myPageService.updateMyPassword(sessionManager.id(), req);

        return CommonResponse
                .success(HttpStatus.NO_CONTENT, "프로필 비밀번호 변경 완료")
                .toResponseEntity();
    }

}
