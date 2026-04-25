package dev.nbcsparta.assignment.commerce_backoffice.enumerate;

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

    public static AccountStatus getEnum(String description) {
        for (AccountStatus status : AccountStatus.values()) {
            if (status.getDescription().equals(description)) {
                return status;
            }
        }
        return null;
    }

    public String getDescription() {
        return description;
    }
}
