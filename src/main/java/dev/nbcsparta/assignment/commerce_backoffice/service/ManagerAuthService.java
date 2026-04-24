package dev.nbcsparta.assignment.commerce_backoffice.service;

import dev.nbcsparta.assignment.commerce_backoffice.repository.ManagerAuthRepository;
import org.springframework.stereotype.Service;

@Service
public class ManagerAuthService {

    private final ManagerAuthRepository managerAuthRepository;

    public ManagerAuthService(ManagerAuthRepository managerAuthRepository) {
        this.managerAuthRepository = managerAuthRepository;
    }
}
