package hsf302.agricultural_products_project.service;

import hsf302.agricultural_products_project.config.VnPayConfig;
import hsf302.agricultural_products_project.dto.PaymentRequest;
import hsf302.agricultural_products_project.dto.PaymentResponse;
import hsf302.agricultural_products_project.dto.PaymentResult;
import hsf302.agricultural_products_project.model.Order;
import hsf302.agricultural_products_project.model.PaymentStatus;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaymentService {

    @Autowired
    private VnPayConfig vnPayConfig;

    @Autowired
    private OrderService orderService;

    public String createPaymentUrl(Long orderId, double amount, String bankCode, HttpServletRequest request) {
        PaymentRequest paymentRequest = new PaymentRequest(amount, orderId, bankCode, request);
        PaymentResponse response = vnPayConfig.createPaymentUrl(paymentRequest);

        if (!response.isSuccess()) {
            throw new RuntimeException("Failed to create payment URL");
        }

        return response.getPaymentUrl();
    }

    public PaymentResult verifyAndProcessPayment(Map<String, String> queryParams) {
        String vnp_TxnRef = queryParams.get("vnp_TxnRef");
        String vnp_ResponseCode = queryParams.get("vnp_ResponseCode");
        String vnp_TransactionNo = queryParams.get("vnp_TransactionNo");
        String vnp_Amount = queryParams.get("vnp_Amount");
        String vnp_BankCode = queryParams.get("vnp_BankCode");
        String vnp_OrderInfo = queryParams.get("vnp_OrderInfo");

        boolean success = "00".equals(vnp_ResponseCode);
        String message = success ? "Payment successful" : "Payment verification failed";

        if (!success) {
            return new PaymentResult(false, message);
        }

        try {
            String[] parts = vnp_TxnRef.split("_");
            Long orderId = Long.parseLong(parts[0]);


            orderService.updatePaymentStatus(orderId, PaymentStatus.COMPLETED);
            Order order = orderService.findOrderById(orderId);

            double amount = Double.parseDouble(vnp_Amount) / 100;

            return new PaymentResult(true, message, order, amount, vnp_TransactionNo, vnp_BankCode);

        } catch (Exception e) {
            throw new RuntimeException("Error processing payment response", e);
        }
    }
}
