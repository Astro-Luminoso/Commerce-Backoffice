package dev.nbcsparta.assignment.commerce_backoffice.controller;

import dev.nbcsparta.assignment.commerce_backoffice.config.CustomUserDetail;
import dev.nbcsparta.assignment.commerce_backoffice.dto.*;
import dev.nbcsparta.assignment.commerce_backoffice.service.MyPageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mypages")
public class MyPageController {

    private final MyPageService myPageService;

    public MyPageController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }

    @GetMapping
    public ResponseEntity<CommonResponse<MyProfileResponse>> getMyProfile(
            @AuthenticationPrincipal CustomUserDetail userDetails
    ) {
        Long managerId = userDetails.getManagerId();
        MyProfileResponse res = myPageService.getMyProfile(managerId);

        return CommonResponse
                .success(HttpStatus.OK, "프로필 조회 완료", res)
                .toResponseEntity();
    }

    @PutMapping
    public ResponseEntity<CommonResponse<MyProfileResponse>> updateMyProfile(
            @Valid @RequestBody UpdateMyProfileRequest req,
            @AuthenticationPrincipal CustomUserDetail userDetails
    ) {
        Long managerId = userDetails.getManagerId();
        MyProfileResponse res = myPageService.updateMyProfile(managerId, req);

        return CommonResponse
                .success(HttpStatus.OK, "프로필 수정 완료", res)
                .toResponseEntity();
    }

    @PatchMapping
    public ResponseEntity<CommonResponse<Void>> updateMyPassword(
            @Valid @RequestBody UpdateMyPasswordRequest req,
            @AuthenticationPrincipal CustomUserDetail userDetails
    ) {
        Long managerId = userDetails.getManagerId();
        myPageService.updateMyPassword(managerId, req);

        return CommonResponse
                .success(HttpStatus.NO_CONTENT, "프로필 비밀번호 변경 완료")
                .toResponseEntity();
    }

}
