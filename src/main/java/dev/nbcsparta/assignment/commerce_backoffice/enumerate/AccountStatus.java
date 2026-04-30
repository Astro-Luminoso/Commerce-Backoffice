package dev.nbcsparta.assignment.commerce_backoffice.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AccountStatus {
    ACTIVE("활성"),
    INACTIVE("비활성"),
    SUSPENDED("정지"),
    PENDING("승인대기"),
    DENIED("정지");

    private final String description;

    AccountStatus(String description) {
        this.description = description;
    }

    @JsonCreator
    public static AccountStatus getEnum(String description) {
        for (AccountStatus status : values()) {
            if (status.getDescription().equals(description)) {
                return status;
            }

            if (status.name().equalsIgnoreCase(description)) {
                return status;
            }
        }
        return null;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }
}
