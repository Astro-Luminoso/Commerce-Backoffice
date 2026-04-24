package dev.nbcsparta.assignment.commerce_backoffice.type;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

/**
 * 관리자 역할 enum
 */
public enum ManagerRole {

    SUPER_ADMIN("슈퍼 관리자"),
    OPERATION_ADMIN("운영 관리자"),
    CS_ADMIN("CS 관리자");

    private final String value;

    ManagerRole(String value) {
        this.value = value;
    }

    @JsonCreator
    public static ManagerRole from(String roleName) {
        return Arrays.stream(values())
                .filter(type -> type.value.equals(roleName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Invalid role: " + roleName));
    }

}
