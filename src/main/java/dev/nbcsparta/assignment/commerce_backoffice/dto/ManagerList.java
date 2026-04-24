package dev.nbcsparta.assignment.commerce_backoffice.dto;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Manager;
import org.springframework.data.domain.Page;

public record ManagerList(Page<ManagerDetail> managerList) {


    public static ManagerList form(Page<Manager> managers) {
        return new ManagerList(managers.map(ManagerDetail::from));
    }

}
