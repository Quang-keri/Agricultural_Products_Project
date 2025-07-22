package hsf302.agricultural_products_project.controller;


import hsf302.agricultural_products_project.dto.OrderDetailDTO;
import hsf302.agricultural_products_project.model.Order;
import hsf302.agricultural_products_project.model.OrderStatus;
import hsf302.agricultural_products_project.model.Role;
import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.service.OrderDetailService;
import hsf302.agricultural_products_project.service.OrderService;
import hsf302.agricultural_products_project.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private OrderDetailService orderDetailService;

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


        return "admin/manageOrder";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/orders/update-status/{id}")
    public String showUpdateStatusForm(@PathVariable("id") Long orderId, Model model) {
        Order order = orderService.getOrderById(orderId);
        model.addAttribute("order", order);
        model.addAttribute("statuses", OrderStatus.values());

        return "admin/order-status-dialog :: updateStatusForm";
    }

    @PostMapping("/orders/update-status")
    public String updateOrderStatus(@RequestParam("orderId") Long orderId,
                                    @RequestParam("orderStatus") OrderStatus orderStatus,
                                    RedirectAttributes redirectAttributes) {
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