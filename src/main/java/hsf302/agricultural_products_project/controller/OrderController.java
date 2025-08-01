package hsf302.agricultural_products_project.controller;

import hsf302.agricultural_products_project.dto.CustomerOrderDto;
import hsf302.agricultural_products_project.dto.OrderProcessDTO;
import hsf302.agricultural_products_project.model.Order;
import hsf302.agricultural_products_project.model.Role;
import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.service.CartService;
import hsf302.agricultural_products_project.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;
    @PostMapping("order/submit")
    public String createOrder(@ModelAttribute CustomerOrderDto customerOrderDto, HttpSession session, Model model, HttpServletRequest request) {
        User account = (User) session.getAttribute("account");
        if (account == null) {

            return "redirect:/login";
        }
        Order order = orderService.createOrder(account, customerOrderDto);
        if("Chuyển khoản ngân hàng".equals(customerOrderDto.getPaymentMethod())){
            request.setAttribute("orderId", order.getOrderId());
            return "forward:/payment/create?amount=" + order.getTotalPrice();
        }
        model.addAttribute("order", order);
        model.addAttribute("success", true);
        cartService.deleteCart(account);
        return "order-confirmation";
    }

    @GetMapping("/orderManagement")
    public String showOrderManagement(HttpSession session, Model model) {
        User account = (User) session.getAttribute("account");
        if (account == null || !Role.ROLE_MEMBER.equals(account.getRole())) {
            return "redirect:/login";
        }

        model.addAttribute("account", account);

        List<OrderProcessDTO> orders = orderService.getOrderManagement(account.getUserId());
        model.addAttribute("orders", orders);
        return "orderManagement";
    }

    @GetMapping("/history")
    public String showOrderHistory(HttpSession session, Model model) {
        User account = (User) session.getAttribute("account");
        if (account == null) {
            return "redirect:/login";
        }

        model.addAttribute("account", account);

        List<OrderProcessDTO> orders = orderService.getOrderHistory(account.getUserId());
        model.addAttribute("orders", orders);
        return "history";
    }
}
