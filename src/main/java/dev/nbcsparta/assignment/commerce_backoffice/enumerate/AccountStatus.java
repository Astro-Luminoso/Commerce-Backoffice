package dev.nbcsparta.assignment.commerce_backoffice.enumerate;

public enum AccountStatus {
    ACTIVE("active"),
    INACTIVE("inactive"),
    SUSPENDED("suspended"),
    PENDING("pending"),
    DENIED("denied");

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
