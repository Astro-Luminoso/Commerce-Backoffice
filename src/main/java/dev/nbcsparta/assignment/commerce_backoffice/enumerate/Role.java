package dev.nbcsparta.assignment.commerce_backoffice.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import dev.nbcsparta.assignment.commerce_backoffice.exception.RoleNotFoundException;

public enum Role {
    CS("CS 관리자", 1),
    OPS("운영 관리자", 2),
    SUPER("슈퍼 관리자", 3);

    private final String roleName;
    private final int accessLevel;

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
    public static Role getEnum(String roleName) {
        for (Role role : values()) {
            if (role.getRoleName().equals(roleName)) {
                return role;
            }

            if (role.name().equalsIgnoreCase(roleName)) {
                return role;
            }
        }
        throw new RoleNotFoundException("Invalid role: " + roleName);
    }
}
