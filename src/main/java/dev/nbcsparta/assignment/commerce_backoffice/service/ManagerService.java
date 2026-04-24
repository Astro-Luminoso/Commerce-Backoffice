package dev.nbcsparta.assignment.commerce_backoffice.service;

import dev.nbcsparta.assignment.commerce_backoffice.repository.ManagerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ManagerService {

    private final ManagerRepository managerRepository;

    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }


}
