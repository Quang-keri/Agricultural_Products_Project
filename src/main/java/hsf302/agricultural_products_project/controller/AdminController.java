package hsf302.agricultural_products_project.controller;


import hsf302.agricultural_products_project.dto.OrderDetailDTO;
import hsf302.agricultural_products_project.model.Order;
import hsf302.agricultural_products_project.model.OrderStatus;
import hsf302.agricultural_products_project.model.Role;
import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.service.OrderDetailService;
import hsf302.agricultural_products_project.service.OrderService;
import hsf302.agricultural_products_project.service.ProductService;
import hsf302.agricultural_products_project.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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

    @Autowired
    private OrderDetailService orderDetailService;

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

        return "redirect:/403";
    }
//
//    @GetMapping("/users")
//    public String userManagement(Model model) {
//        model.addAttribute("users", userService.findAll());
//        return "admin/manageUser";
//    }


    @GetMapping("/orders")
    public String orderManagement(Model model,
                                  HttpSession session,
                                  @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo) {
        User user = (User) session.getAttribute("account");
        if (user == null || !user.getRole().equals(Role.ROLE_ADMIN)) {

            return "redirect:/403";
        }
        model.addAttribute("user", user);

        Page<Order> orders = orderService.getAllOrders(pageNo);

        model.addAttribute("totalPage", orders.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("orders", orders);


        return "admin/manageOrder";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/orders/update-status/{id}")
    public String showUpdateStatusForm(@PathVariable("id") Long orderId, Model model,HttpSession session) {
        User user = (User) session.getAttribute("account");
        if (user == null || !user.getRole().equals(Role.ROLE_ADMIN)) {

            return "redirect:/403";
        }
        Order order = orderService.getOrderById(orderId);
        model.addAttribute("order", order);
        model.addAttribute("statuses", OrderStatus.values());

        return "admin/order-status-dialog :: updateStatusForm";
    }

    @PostMapping("/orders/update-status")
    public String updateOrderStatus(@RequestParam("orderId") Long orderId,
                                    @RequestParam("orderStatus") OrderStatus orderStatus,
                                    RedirectAttributes redirectAttributes,HttpSession session) {
        User user = (User) session.getAttribute("account");
        if (user == null || !user.getRole().equals(Role.ROLE_ADMIN)) {
            return "redirect:/403";
        }
        
        orderService.updateOrderStatus(orderId, orderStatus);
        redirectAttributes.addFlashAttribute("success", "Cập nhật trạng thái thành công!");
        return "redirect:/admin/orders";
    }

    @GetMapping("/orders/details/{id}")
    @ResponseBody
    public List<OrderDetailDTO> getOrderDetails(@PathVariable("id") int orderId) {
        return orderDetailService.getOrderDetailDTOsByOrderId(orderId);
    }


}