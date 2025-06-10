package hsf302.agricultural_products_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

     @GetMapping("/")
    public String adminDashboard() {

         return "admin/admin";
     }
}
