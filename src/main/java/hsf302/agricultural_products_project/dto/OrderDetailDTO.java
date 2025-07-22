package hsf302.agricultural_products_project.dto;

import java.math.BigDecimal;

public class OrderDetailDTO {
    private String productName;
    private int quantity;
    private BigDecimal totalPrice;

    public OrderDetailDTO(String productName, BigDecimal totalPrice, int quantity) {
        this.productName = productName;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
