package hsf302.agricultural_products_project.controller;

import hsf302.agricultural_products_project.dto.CustomerOrderDto;
import hsf302.agricultural_products_project.model.Order;
import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("order/submit")
    public String createOrder(@ModelAttribute CustomerOrderDto customerOrderDto, HttpSession session, Model model, HttpServletRequest request) {
        User account = (User) session.getAttribute("account");
        if (account == null) {
            // Nếu người dùng chưa đăng nhập, chuyển hướng đến trang đăng nhập
            return "redirect:/login";
        }
        Order order = orderService.createOrder(account, customerOrderDto);
        if("Chuyển khoản ngân hàng".equals(customerOrderDto.getPaymentMethod())){
            request.setAttribute("orderId", order.getOrderId());
            return "forward:/payment/create?amount=" + order.getTotalPrice();
        }
        model.addAttribute("order", order);
        model.addAttribute("success", true);
        return "order-confirmation";
    }
}
