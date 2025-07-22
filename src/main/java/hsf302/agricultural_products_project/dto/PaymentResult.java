package hsf302.agricultural_products_project.dto;

import hsf302.agricultural_products_project.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResult {
    private boolean success;
    private String message;
    private Order order;
    private double amount;
    private String transactionNo;
    private String bankCode;

    public PaymentResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public String getFormattedAmount() {
        return String.format("%,.0f", amount);
    }
}
