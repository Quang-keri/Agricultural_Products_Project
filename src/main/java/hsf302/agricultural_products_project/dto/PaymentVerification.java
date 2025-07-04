package hsf302.agricultural_products_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentVerification {
    private boolean success;
    private String message;
    private String transactionRef;
    private String responseCode;

    public PaymentVerification(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success && "00".equals(responseCode) && transactionRef != null;
    }

}
