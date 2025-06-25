package hsf302.agricultural_products_project.controller;

import hsf302.agricultural_products_project.config.SecurityUtil;
import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.service.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// Uncomment these imports if you have ProductService/OrderService and want to fetch data
// import hsf302.agricultural_products_project.service.ProductService;
// import hsf302.agricultural_products_project.service.OrderService;
// import java.util.List;

@Slf4j
@Controller

@RequestMapping("/admin")
public class AdminController {



    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        // này là demo 2 cái lấy thông tin user lun ae nào ko bik nhìn qua đây
//        String username = SecurityUtil.getSessionUsername();
        User user = SecurityUtil.getUserWithoutPassword();
        System.out.println("User: " + user.getUserName() + ", Role: " + user.getRole());
        log.info("Authentication: {}", user);

        return "admin/admindashboard";
    }

    @GetMapping("/users")
    public String userManagement(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof CustomUserDetails) {

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User currentUser = userDetails.getUser();
            model.addAttribute("user", currentUser);

            // In a real application, you would fetch the list of users here
            // List<User> users = userService.getAllUsers();
            // model.addAttribute("users", users);
        }
        return "admin/manageUser";
    }

    // --- NEW: Product Management ---
    @GetMapping("/products")
    public String productManagement(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof CustomUserDetails) {

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User currentUser = userDetails.getUser();
            model.addAttribute("user", currentUser);

            // In a real application, you would fetch the list of products here
            // List<Product> products = productService.getAllProducts();
            // model.addAttribute("products", products);
        }
        return "admin/manageProduct"; // Tên file HTML sẽ là manageProduct.html
    }

    // --- NEW: Order Management ---
    @GetMapping("/orders")
    public String orderManagement(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof CustomUserDetails) {

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User currentUser = userDetails.getUser();
            model.addAttribute("user", currentUser);

            // In a real application, you would fetch the list of orders here
            // List<Order> orders = orderService.getAllOrders();
            // model.addAttribute("orders", orders);
        }
        return "admin/manageOrder"; // Tên file HTML sẽ là manageOrder.html
    }
}