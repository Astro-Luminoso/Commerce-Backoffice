package dev.nbcsparta.assignment.commerce_backoffice.service;

import dev.nbcsparta.assignment.commerce_backoffice.config.PasswordEncoder;
import dev.nbcsparta.assignment.commerce_backoffice.dto.MyProfileResponse;
import dev.nbcsparta.assignment.commerce_backoffice.dto.UpdateMyPasswordRequest;
import dev.nbcsparta.assignment.commerce_backoffice.dto.UpdateMyProfileRequest;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Manager;
import dev.nbcsparta.assignment.commerce_backoffice.exception.ConflictUserException;
import dev.nbcsparta.assignment.commerce_backoffice.exception.ManagerNotFoundException;
import dev.nbcsparta.assignment.commerce_backoffice.exception.NotMatchException;
import dev.nbcsparta.assignment.commerce_backoffice.repository.ManagerAuthRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyPageService {

    private final ManagerAuthRepository managerAuthRepository;
    private final PasswordEncoder passwordEncoder;

    public MyPageService(ManagerAuthRepository managerAuthRepository, PasswordEncoder passwordEncoder) {
        this.managerAuthRepository = managerAuthRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public MyProfileResponse getMyProfile(Long managerId) {
        Manager manager = managerAuthRepository.getReferenceById(managerId);
        return MyProfileResponse.from(manager);
    }

    @Transactional
    public MyProfileResponse updateMyProfile(Long managerId, UpdateMyProfileRequest req) {
        Manager manager = managerAuthRepository.getReferenceById(managerId);
        if (!manager.getEmail().equals(req.email()) && managerAuthRepository.existsByEmail(req.email())) {
            throw new ConflictUserException("이미 존재하는 사용자입니다.");
        }
        manager.updateProfile(req);
        return MyProfileResponse.from(manager);
    }

    @Transactional
    public void updateMyPassword(Long managerId, UpdateMyPasswordRequest req) {
        Manager manager = managerAuthRepository.findById(managerId)
                .orElseThrow(ManagerNotFoundException::new);

        // 기존 비밀번호 일치하는지 검증
        boolean isMatch = manager.isPasswordMatch(passwordEncoder, req.password());
        if (!isMatch) {
            throw new NotMatchException("비밀번호가 일치하지 않습니다.");
        }

        // 새 비밀번호와 기존 비밀번호가 다른지 검증
        boolean isMatchNew = manager.isPasswordMatch(passwordEncoder, req.newPassword());
        if (isMatchNew) {
            throw new NotMatchException("새 비밀번호는 기존 비밀번호와 달라야 합니다.");
        }

        // 새 비밀번호 암호화 및 저장
        String encodedNewPassword = passwordEncoder.encode(req.newPassword());
        manager.updatePassword(encodedNewPassword);
    }
}
