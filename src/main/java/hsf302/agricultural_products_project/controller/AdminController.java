package hsf302.agricultural_products_project.controller;

import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/")
    public String adminDashboard(Model model) {
        List<User> members = userService.findAll();
        model.addAttribute("members", members);
        return "admin/admin";
    }

    @GetMapping("/update-status/{id}")
    public String updateStatus(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        if (user != null) {
            String currentStatus = user.getStatus();
            if ("active".equalsIgnoreCase(currentStatus)) {
                user.setStatus("deactive");
            } else {
                user.setStatus("active");
            }
            userService.save(user);
        }
        return "redirect:/admin/";
    }
}
