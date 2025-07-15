package hsf302.agricultural_products_project.controller;


import hsf302.agricultural_products_project.model.Role;
import hsf302.agricultural_products_project.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller

@RequestMapping("/admin")
public class AdminController {



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

        return "admin/manageUser";
    }


    @GetMapping("/products")
    public String productManagement(Model model) {
        return "admin/manageProduct";
    }


    @GetMapping("/orders")
    public String orderManagement(Model model) {
        return "admin/manageOrder";
    }
}