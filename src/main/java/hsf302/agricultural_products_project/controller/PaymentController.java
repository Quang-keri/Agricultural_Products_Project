package hsf302.agricultural_products_project.controller;



import hsf302.agricultural_products_project.dto.PaymentResult;

import hsf302.agricultural_products_project.model.User;

import hsf302.agricultural_products_project.service.PaymentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/payment")
@Slf4j
public class PaymentController {



    @Autowired
    private PaymentService paymentService;



    @PostMapping("/create")
    public String createPayment(
            @RequestParam("amount") double amount,
            @RequestParam(value = "bankCode", required = false) String bankCode,
            HttpServletRequest request,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("account");
        if (user == null) {
            return "redirect:/login";
        }

        Long orderId = (Long) request.getAttribute("orderId");
        if (orderId == null || orderId < 1) {
            return "redirect:/cart";
        }

        try {
            String paymentUrl = paymentService.createPaymentUrl(orderId, amount, bankCode, request);
            return "redirect:" + paymentUrl;
        } catch (Exception e) {
            log.error("Error creating payment", e);
            return "redirect:/error?message=payment_creation_failed";
        }
    }

    @GetMapping("/vnpayReturn")
    public String handleVnPayReturn(@RequestParam Map<String, String> params, Model model) {
        try {
            PaymentResult result = paymentService.verifyAndProcessPayment(params);

            model.addAttribute("success", result.isSuccess());

            if (result.isSuccess()) {
                model.addAttribute("paymentMessage", result.getMessage());
                model.addAttribute("order", result.getOrder());
                model.addAttribute("amount", result.getFormattedAmount());
                model.addAttribute("transactionNo", result.getTransactionNo());
                model.addAttribute("bankCode", result.getBankCode());
            } else {
                model.addAttribute("errorMessage", result.getMessage());
            }

        } catch (Exception e) {
            log.error("Error verifying payment", e);
            model.addAttribute("success", false);
            model.addAttribute("errorMessage", "System error occurred");
        }
        return "order-confirmation";
    }


    @GetMapping("/payment-form.html")
    public String showPaymentForm() {
        return "payment-form";
    }
}
