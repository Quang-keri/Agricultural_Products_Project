package hsf302.agricultural_products_project.dto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentRequest {
    private double amount;
    private Long orderId;
    private String bankCode;
    private HttpServletRequest request;
}
