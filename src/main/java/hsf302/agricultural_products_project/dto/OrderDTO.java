package hsf302.agricultural_products_project.dto;

public class OrderDTO {
    private Long userId;
    private double totalAmount;
    private String paymentMethod;

    public OrderDTO() {
    }

    public OrderDTO(Long userId, double totalAmount, String paymentMethod) {
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}

