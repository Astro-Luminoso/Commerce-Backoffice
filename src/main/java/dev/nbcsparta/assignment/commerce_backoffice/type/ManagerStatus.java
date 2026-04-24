package dev.nbcsparta.assignment.commerce_backoffice.type;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

/**
 * 관리자 상태 enum
 */
public enum ManagerStatus {
    ACTIVE("활성"),
    INACTIVE("비활성"),
    SUSPENDED("정지"),
    PENDING("승인 대기"),
    REJECTED("거부");

    private final String value;

    ManagerStatus(String value) {
        this.value = value;
    }

    @JsonCreator
    public static ManagerStatus from(String statusName) {
        return Arrays.stream(values())
                .filter(type -> type.value.equals(statusName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Invalid status: " + statusName));
    }
}
