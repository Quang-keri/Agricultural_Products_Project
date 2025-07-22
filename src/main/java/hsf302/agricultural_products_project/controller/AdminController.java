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
            @RequestParam(value = "range", required = false) String range,
            Model model,
            HttpSession session) {

        User user = (User) session.getAttribute("account");

        if (user != null && user.getRole().equals(Role.ROLE_ADMIN)) {
            model.addAttribute("account", user);
            model.addAttribute("user", user);
            model.addAttribute("range", range);
            Long userId = userService.countUsers();
            model.addAttribute("userId", userId);
            Long orderId = orderService.countOrders();
            model.addAttribute("orderId", orderId);
            Long  productId = productService.countProducts();
            model.addAttribute("productId", productId);

            List<User> users = userService.getRecentUsers(5);
            model.addAttribute("recentUsers", users);

            List<Order> orders = orderService.getAllOrders();

            // Xử lý lọc theo thời gian
            if (range != null && !range.isEmpty()) {
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime start = null;
                LocalDateTime end = null;

                switch (range) {
                    case "this-week" -> {
                        start = now.with(DayOfWeek.MONDAY).toLocalDate().atStartOfDay();
                        end = start.plusDays(6).with(LocalTime.MAX);
                    }
                    case "last-week" -> {
                        start = now.minusWeeks(1).with(DayOfWeek.MONDAY).toLocalDate().atStartOfDay();
                        end = start.plusDays(6).with(LocalTime.MAX);
                    }
                    case "this-month" -> {
                        start = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
                        end = start.plusMonths(1).minusDays(1).with(LocalTime.MAX);
                    }
                    case "last-month" -> {
                        start = now.minusMonths(1).withDayOfMonth(1).toLocalDate().atStartOfDay();
                        end = start.plusMonths(1).minusDays(1).with(LocalTime.MAX);
                    }
                    case "this-year" -> {
                        start = now.withDayOfYear(1).toLocalDate().atStartOfDay();
                        end = start.plusYears(1).minusDays(1).with(LocalTime.MAX);
                    }
                    case "last-year" -> {
                        start = now.minusYears(1).withDayOfYear(1).toLocalDate().atStartOfDay();
                        end = start.plusYears(1).minusDays(1).with(LocalTime.MAX);
                    }
                }

                if (start != null && end != null) {
                    LocalDateTime finalStart = start;
                    LocalDateTime finalEnd = end;
                    orders = orders.stream()
                            .filter(o -> o.getCreateAt() != null &&
                                    !o.getCreateAt().isBefore(finalStart) &&
                                    !o.getCreateAt().isAfter(finalEnd))
                            .toList();
                }
            }

            // Đếm đơn hàng theo trạng thái
            long pendingCount = orders.stream().filter(o -> o.getOrderStatus() == OrderStatus.PENDING).count();
            long confirmedCount = orders.stream().filter(o -> o.getOrderStatus() == OrderStatus.CONFIRMED).count();
            long shippedCount = orders.stream().filter(o -> o.getOrderStatus() == OrderStatus.SHIPPED).count();
            long deliveredCount = orders.stream().filter(o -> o.getOrderStatus() == OrderStatus.DELIVERED).count();
            long cancelledCount = orders.stream().filter(o -> o.getOrderStatus() == OrderStatus.CANCELLED).count();

            model.addAttribute("pendingCount", pendingCount);
            model.addAttribute("confirmedCount", confirmedCount);
            model.addAttribute("shippedCount", shippedCount);
            model.addAttribute("deliveredCount", deliveredCount);
            model.addAttribute("cancelledCount", cancelledCount);

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