package hsf302.agricultural_products_project.controller;


import hsf302.agricultural_products_project.model.Role;
import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {
    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public String index(HttpSession session, Model model) {
        User account = (User) session.getAttribute("account");
        System.out.println("Session Account: " + account);
        if (account != null) {
            model.addAttribute("account", account);
            return "index";
        }
        return "index";
    }

    @GetMapping("/about-us")
    public String aboutUs(Model model, HttpSession session) {
        User account = (User) session.getAttribute("account");

        if (account != null) {
            model.addAttribute("account", account);
            return "about_us";
        }
        return "redirect:/403";
    }
}
