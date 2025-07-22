package hsf302.agricultural_products_project.controller;

import hsf302.agricultural_products_project.model.Role;
import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

   @GetMapping("/admin/users")
    public String userManagement(Model model, HttpSession session) {
       User account = (User) session.getAttribute("account");

       if (account != null && account.getRole().equals(Role.ROLE_ADMIN)) {
           model.addAttribute("user", account);
           model.addAttribute("users", userService.findAll());
           return "admin/user/manageUser";
       }
         return "redirect:/error-page";
    }
    @GetMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.updateStatus(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model, HttpSession session) {
        User account = (User) session.getAttribute("account");

        if (account != null && account.getRole().equals(Role.ROLE_ADMIN)) {
            model.addAttribute("user", account);
            User user = userService.findById(id);
            model.addAttribute("user", user);
            return "admin/user/editUser";
        }
        return "redirect:/error-page";
    }
    @PostMapping("/admin/users/edit/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute User user, Model model, HttpSession session) {
        User account = (User) session.getAttribute("account");

        if (account != null && account.getRole().equals(Role.ROLE_ADMIN)) {
            userService.updateUser(user);
            return "redirect:/admin/users";
        }
        return "redirect:/error-page";
    }
}
