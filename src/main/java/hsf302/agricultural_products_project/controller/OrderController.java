package hsf302.agricultural_products_project.controller;

import hsf302.agricultural_products_project.dto.CustomerOrderDto;
import hsf302.agricultural_products_project.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrderController {

    @PostMapping("order/submit")
    public String createOrder(@ModelAttribute CustomerOrderDto customerOrderDto, HttpSession session, Model model) {
        User account = (User) session.getAttribute("account");
        if (account == null) {
            System.err.println("User not logged in, redirecting to login page at order controller line 16.");
            return "redirect:/login";
        }
        return "redirect:/order-confirmation";
    }
}
