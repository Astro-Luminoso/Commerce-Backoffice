package dev.nbcsparta.assignment.commerce_backoffice.controller;

import dev.nbcsparta.assignment.commerce_backoffice.service.ManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/managers")
public class ManagerController {

    private final ManagerService managerService;

    public ManagerController(ManagerService managerRepository) {
        this.managerService = managerRepository;
    }

}
