package dev.nbcsparta.assignment.commerce_backoffice.controller;

import dev.nbcsparta.assignment.commerce_backoffice.service.ManagerAuthService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManagerAuthController {

    private final ManagerAuthService managerAuthService;

    public ManagerAuthController(ManagerAuthService managerAuthService) {
        this.managerAuthService = managerAuthService;
    }
}
