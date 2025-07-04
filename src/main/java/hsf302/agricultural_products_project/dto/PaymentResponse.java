package hsf302.agricultural_products_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponse {
    private boolean success;
    private String paymentUrl;
    private String errorMessage;
}
