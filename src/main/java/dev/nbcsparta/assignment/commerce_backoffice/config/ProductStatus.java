package dev.nbcsparta.assignment.commerce_backoffice.config;

public enum ProductStatus {

    SALE("판매중"),
    SOLD_OUT("품절"),
    DISCONTINUED("단종");

    private final String status;

    ProductStatus(String description) {
        this.status = description;
    }

    public String getStatus() {
        return status;
    }
}