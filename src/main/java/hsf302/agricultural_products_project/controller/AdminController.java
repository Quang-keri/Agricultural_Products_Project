package hsf302.agricultural_products_project.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller

@RequestMapping("/admin")
public class AdminController {



    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {



        return "admin/admindashboard";
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