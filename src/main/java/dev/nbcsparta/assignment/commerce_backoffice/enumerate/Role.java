package dev.nbcsparta.assignment.commerce_backoffice.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum Role {
    CS_MANAGER("CS_Manager", 1),
    Ops_MANAGER("Ops_Manager", 2),
    Super_MANAGER("Super_Manager", 3);

    private final String roleName;
    private int accessLevel;

    Role(String roleName, int accessLevel) {
        this.roleName = roleName;
        this.accessLevel = accessLevel;
    }

    @JsonValue
    public String getRoleName() {
        return this.roleName;
    }

    public int getAccessLevel() {
        return this.accessLevel;
    }

    @JsonCreator
    public static Role from(String roleName) {
        return Arrays.stream(values())
                .filter(type -> type.roleName.equals(roleName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Invalid role: " + roleName));
    }
}
