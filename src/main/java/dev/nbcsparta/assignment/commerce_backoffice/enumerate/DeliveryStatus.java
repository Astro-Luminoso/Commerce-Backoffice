package dev.nbcsparta.assignment.commerce_backoffice.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DeliveryStatus {

    PENDING("준비중"),
    PROCESSING("배송중"),
    PROCESSED("배송완료"),
    CANCELLED("취소됨");

    private final String description;

    DeliveryStatus(String description) {
        this.description = description;
    }

    @JsonCreator
    public static DeliveryStatus getEnum(String description) {
        for (DeliveryStatus status : values()) {
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
