package hsf302.agricultural_products_project.controller;

import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.service.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

     @GetMapping("/")
    public String adminDashboard(Model model) {
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         if (authentication != null && authentication.isAuthenticated()
                 && authentication.getPrincipal() instanceof CustomUserDetails) {

             CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
             User user = userDetails.getUser();
             System.out.println("User: " + user.getUserName() + ", Role: " + user.getRole());
             model.addAttribute("user", user);
         }
         return "admin/admin";
     }

}
