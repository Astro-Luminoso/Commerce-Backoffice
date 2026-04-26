package dev.nbcsparta.assignment.commerce_backoffice.service;

import dev.nbcsparta.assignment.commerce_backoffice.dto.MyProfileResponse;
import dev.nbcsparta.assignment.commerce_backoffice.dto.UpdateMyProfileRequest;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Manager;
import dev.nbcsparta.assignment.commerce_backoffice.exception.ConflictUserException;
import dev.nbcsparta.assignment.commerce_backoffice.repository.ManagerAuthRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyPageService {

    private final ManagerAuthRepository managerAuthRepository;

    public MyPageService(ManagerAuthRepository managerAuthRepository) {
        this.managerAuthRepository = managerAuthRepository;
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
}
