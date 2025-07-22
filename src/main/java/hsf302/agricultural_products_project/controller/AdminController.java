package hsf302.agricultural_products_project.controller;


import hsf302.agricultural_products_project.model.Order;
import hsf302.agricultural_products_project.model.OrderStatus;
import hsf302.agricultural_products_project.model.Role;
import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.service.OrderService;
import hsf302.agricultural_products_project.service.ProductService;
import hsf302.agricultural_products_project.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


import java.time.LocalDate;
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

    @Autowired
    private ProductService productService;

//    @GetMapping("/dashboard")
//    public String adminDashboard(HttpSession session, Model model) {
//        User account = (User) session.getAttribute("account");
//
//        if (account != null && account.getRole().equals(Role.ROLE_ADMIN)) {
//            model.addAttribute("account", account);
//            model.addAttribute("user", account);
//            return "admin/admindashboard";
//        }
//        return "redirect:/error-page"; // Chuyển hướng đến trang lỗi nếu không phải admin
//    }

    @GetMapping("/dashboard")
    public String showDashboard(
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "year", required = false) Integer year,
            Model model,
            HttpSession session) {

        User user = (User) session.getAttribute("account");

        if (user != null && user.getRole().equals(Role.ROLE_ADMIN)) {
            model.addAttribute("account", user);
            model.addAttribute("user", user);
            model.addAttribute("month", month);
            model.addAttribute("year", year);

            model.addAttribute("userId", userService.countUsers());
            model.addAttribute("orderId", orderService.countOrders());
            model.addAttribute("productId", productService.countProducts());
            model.addAttribute("recentUsers", userService.getRecentUsers(5));

            List<Order> orders = orderService.getAllOrders();

            // Nếu chọn cả tháng và năm thì lọc
            if (month != null && year != null) {
                LocalDateTime start = LocalDate.of(year, month, 1).atStartOfDay();
                LocalDateTime end = start.plusMonths(1).minusDays(1).with(LocalTime.MAX);
                orders = orders.stream()
                        .filter(o -> o.getCreateAt() != null &&
                                !o.getCreateAt().isBefore(start) &&
                                !o.getCreateAt().isAfter(end))
                        .toList();
            }

            // Đếm đơn hàng theo trạng thái
            model.addAttribute("pendingCount", orders.stream().filter(o -> o.getOrderStatus() == OrderStatus.PENDING).count());
            model.addAttribute("confirmedCount", orders.stream().filter(o -> o.getOrderStatus() == OrderStatus.CONFIRMED).count());
            model.addAttribute("shippedCount", orders.stream().filter(o -> o.getOrderStatus() == OrderStatus.SHIPPED).count());
            model.addAttribute("deliveredCount", orders.stream().filter(o -> o.getOrderStatus() == OrderStatus.DELIVERED).count());
            model.addAttribute("cancelledCount", orders.stream().filter(o -> o.getOrderStatus() == OrderStatus.CANCELLED).count());

            // Gửi danh sách năm gần đây (ví dụ: 2022 - 2025)
            List<Integer> years = List.of(2022, 2023, 2024, 2025);
            model.addAttribute("years", years);

            return "admin/admindashboard";
        }

        return "redirect:/error-page";
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