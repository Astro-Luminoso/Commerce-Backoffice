package dev.nbcsparta.assignment.commerce_backoffice.service;

import dev.nbcsparta.assignment.commerce_backoffice.config.PasswordEncoder;
import dev.nbcsparta.assignment.commerce_backoffice.dto.manager.CreateManagerRequest;
import dev.nbcsparta.assignment.commerce_backoffice.dto.manager.CreateManagerResponse;
import dev.nbcsparta.assignment.commerce_backoffice.dto.LoginRequest;
import dev.nbcsparta.assignment.commerce_backoffice.dto.SessionManager;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Manager;
import dev.nbcsparta.assignment.commerce_backoffice.exception.ConflictUserException;
import dev.nbcsparta.assignment.commerce_backoffice.exception.LoginNotAllowedException;
import dev.nbcsparta.assignment.commerce_backoffice.exception.NotMatchException;
import dev.nbcsparta.assignment.commerce_backoffice.repository.ManagerAuthRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManagerAuthService {

    private final ManagerAuthRepository managerAuthRepository;
    private final PasswordEncoder passwordEncoder;

    public ManagerAuthService(ManagerAuthRepository managerAuthRepository, PasswordEncoder passwordEncoder) {
        this.managerAuthRepository = managerAuthRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public CreateManagerResponse register(CreateManagerRequest req) {
        if (managerAuthRepository.existsByEmail(req.email())) {
            throw new ConflictUserException("이미 존재하는 사용자입니다.");
        }

        String encodedPassword = passwordEncoder.encode(req.password());
        Manager manager = Manager.to(req, encodedPassword);
        Manager resManager = managerAuthRepository.save(manager);

        return CreateManagerResponse.from(resManager);
    }

    @Transactional
    public SessionManager login(LoginRequest req) {
        Manager manager = managerAuthRepository.findByEmail(req.email())
                .orElseThrow(() -> new NotMatchException("이메일 또는 비밀번호가 일치하지 않습니다."));

        boolean isMatch = manager.isPasswordMatch(passwordEncoder, req.password());
        if (!isMatch) {
            throw new NotMatchException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        switch (manager.getStatus()) {
            case ACTIVE -> {
                return SessionManager.from(manager);
            }
            case INACTIVE -> throw new LoginNotAllowedException("계정 비활성화 상태입니다.");
            case SUSPENDED -> throw new LoginNotAllowedException("계정이 정지된 상태입니다.");
            case PENDING -> throw new LoginNotAllowedException("계정 승인대기 중입니다.");
            case DENIED -> throw new LoginNotAllowedException("계정 신청이 거부된 상태입니다.");
            default -> throw new IllegalStateException("유효하지 않은 상태 값: " + manager.getStatus());
        }
    }

}
