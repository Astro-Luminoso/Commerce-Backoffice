package dev.nbcsparta.assignment.commerce_backoffice.service;

import dev.nbcsparta.assignment.commerce_backoffice.config.PasswordEncoder;
import dev.nbcsparta.assignment.commerce_backoffice.dto.CreateManagerRequest;
import dev.nbcsparta.assignment.commerce_backoffice.dto.CreateManagerResponse;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Manager;
import dev.nbcsparta.assignment.commerce_backoffice.exception.ConflictUserException;
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

}
