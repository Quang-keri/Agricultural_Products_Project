package hsf302.agricultural_products_project.controller;


import hsf302.agricultural_products_project.model.Order;
import hsf302.agricultural_products_project.model.Role;
import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.service.OrderService;
import hsf302.agricultural_products_project.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller

@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        User account = (User) session.getAttribute("account");

        if (account != null && account.getRole().equals(Role.ROLE_ADMIN)) {
            model.addAttribute("account", account);
            return "admin/admindashboard";
        }
        return "redirect:/403";
    }

    @GetMapping("/users")
    public String userManagement(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/manageUser";
    }


    @GetMapping("/products")
    public String productManagement(Model model) {
        return "admin/manageProduct";
    }


    @GetMapping("/orders")
    public String orderManagement(Model model, HttpSession session) {
        User user = (User) session.getAttribute("account");
        model.addAttribute("user", user);

        List<Order> orders = orderService.getAllOrders();
        Map<Long, String> orderDates = new HashMap<>();
        for (Order order : orders) {
            orderDates.put(order.getOrderId(), order.getCreateAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        model.addAttribute("orders", orders);
        model.addAttribute("orderDates", orderDates);


        return "admin/manageOrder"; // File HTML bạn gửi bên trên
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Xóa toàn bộ session, bao gồm cả "loggedInUser"
        return "redirect:/login"; // Chuyển hướng về trang đăng nhập
    }
}