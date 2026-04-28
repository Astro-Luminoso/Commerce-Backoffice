package dev.nbcsparta.assignment.commerce_backoffice.dto.manager;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Manager;
import org.springframework.data.domain.Page;

import java.util.List;

public record ManagerListDetail(List<ManagerDetail> managerList, int page, int size, int total, int totalPage) {


    public static ManagerListDetail from(Page<Manager> managers) {
        Page<ManagerDetail> managerListPage = managers.map(ManagerDetail::from);
        List<ManagerDetail> managerList = managerListPage.getContent();
        return new ManagerListDetail(managerList,
                managerListPage.getNumber() + 1,
                managerListPage.getSize(),
                managerList.size(),
                managerListPage.getTotalPages());
    }

}
