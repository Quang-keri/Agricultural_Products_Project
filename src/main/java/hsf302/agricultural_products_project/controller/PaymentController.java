package hsf302.agricultural_products_project.controller;


import hsf302.agricultural_products_project.config.VnPayConfig;
import hsf302.agricultural_products_project.dto.CustomerOrderDto;
import hsf302.agricultural_products_project.dto.PaymentRequest;
import hsf302.agricultural_products_project.dto.PaymentResponse;
import hsf302.agricultural_products_project.dto.PaymentVerification;
import hsf302.agricultural_products_project.model.PaymentStatus;
import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/payment")
@Slf4j
public class PaymentController {

    @Autowired
    private VnPayConfig vnPayConfig;

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public String createPayment(
            @RequestParam("amount") double amount,
            @RequestParam(value = "bankCode", required = false) String bankCode,
            HttpServletRequest request, HttpSession session, Model model ) {
        try {
            //  Tạo Order trong DB
            User account = (User) session.getAttribute("account");
            if (account == null) {
                System.err.println("User not logged in, redirecting to login page at payment controller line 42.");
                return "redirect:/login";
            }
            //Khi qua trang thanh toán thì thông tin như: tên, địa chỉ, số điện thoại sẽ được lấy từ CustomerOrderDto
            //Nên thêm @ModelAttribute CustomerOrderDto customerOrderDto vào hàm createPayment
            //tạo order dùng cái CustomerOrderDto để tạo order, t refactor lại method
            //createOrder lai roi, check lai
            Long userId = account.getUserId();
            Long orderId = (Long) request.getAttribute("orderId");
           // Long orderId = orderService.createOrder(userId, amount); //
            if (orderId == null || orderId < 1) {
                return "redirect:/cart";
            }

            // Tạo yêu cầu thanh toán VNPay
            PaymentRequest paymentRequest = new PaymentRequest(amount, orderId, bankCode, request);
            PaymentResponse response = vnPayConfig.createPaymentUrl(paymentRequest);

            if (!response.isSuccess()) {
                log.error("Payment creation failed: {}", response.getErrorMessage());
                return "redirect:/error?message=payment_creation_failed";
            }

            //  Redirect đến VNPay
            return "redirect:" + response.getPaymentUrl();

        } catch (Exception e) {
            log.error("Error creating payment: ", e);
            return "redirect:/error?message=system_error";
        }
    }
    //cap nhat lai phuong thuc nay de xu ly thanh toan tra ve tu VNPay, order-confirmation.html
    @GetMapping("/vnpayReturn")
    public String paymentReturn(@RequestParam Map<String, String> queryParams, Model model) {
        try {
            String vnp_TxnRef = queryParams.get("vnp_TxnRef");
            String vnp_ResponseCode = queryParams.get("vnp_ResponseCode");
            String vnp_TransactionNo = queryParams.get("vnp_TransactionNo");
            String vnp_Amount = queryParams.get("vnp_Amount");
            String vnp_BankCode = queryParams.get("vnp_BankCode");
            String vnp_OrderInfo = queryParams.get("vnp_OrderInfo");

            // Log the received parameters
            log.info("Received payment return - TxnRef: {}, ResponseCode: {}, Amount: {}",
                    vnp_TxnRef, vnp_ResponseCode, vnp_Amount);

            // Verify the payment response
            PaymentVerification verification = new PaymentVerification(
                    "00".equals(vnp_ResponseCode),
                    vnp_OrderInfo,
                    vnp_TxnRef,
                    vnp_ResponseCode
            );

            if (verification.isSuccess()) {
                try {
                    String[] parts = vnp_TxnRef.split("_");
                    Long orderId = Long.parseLong(parts[0]);
                    orderService.updatePaymentStatus(orderId, PaymentStatus.COMPLETED);

                    // Convert amount from VNPay format (x100)
                    double actualAmount = Double.parseDouble(vnp_Amount) / 100;

                    model.addAttribute("success", true);
                    model.addAttribute("message", "Payment successful");
                    //Thay orderId bằng Order,
                    //Trang thông báo nhận một object Order để hiển thị thông tin
                    // Thông tin orderId, amount, transactionNo, bankCode sẽ được hiển thị trong order-confirmation.html
                    //Nhớ check  xem trên ui có hiển thị đúng thông tin không
                    model.addAttribute("order", orderId);
                    model.addAttribute("amount", String.format("%,.0f", actualAmount));
                    model.addAttribute("transactionNo", vnp_TransactionNo);
                    model.addAttribute("bankCode", vnp_BankCode);

                    log.info("Payment successful for order: {}", orderId);

                } catch (NumberFormatException e) {
                    log.error("Error parsing order ID: {} - {}", vnp_TxnRef, e.getMessage());
                    model.addAttribute("success", false);
                    model.addAttribute("message", "Invalid order reference");
                }
            } else {
                log.warn("Payment failed - ResponseCode: {}", vnp_ResponseCode);
                model.addAttribute("success", false);
                model.addAttribute("message", "Payment verification failed");
            }

            return "order-confirmation";

        } catch (Exception e) {
            log.error("Error processing payment return", e);
            model.addAttribute("success", false);
            model.addAttribute("message", "System error occurred");
            return "order-confirmation";
        }
    }
    @GetMapping("/payment-form.html")
    public String showPaymentForm() {
        return "payment-form";
    }
}
