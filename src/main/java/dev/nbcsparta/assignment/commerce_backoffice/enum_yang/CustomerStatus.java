package dev.nbcsparta.assignment.commerce_backoffice.enum_yang;

public enum CustomerStatus {
    ACTIVE("활성"),
    INACTIVE("비활성"),
    SUSPENDED("정지");

    private final String description;

    CustomerStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
