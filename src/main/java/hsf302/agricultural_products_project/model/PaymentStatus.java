package hsf302.agricultural_products_project.model;

public enum PaymentStatus {
    PENDING("Chưa thanh toán"),
    COMPLETED("Đã thanh toán"),
    FAILED("Thanh toán thất bại");

    private final String status;

    PaymentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
