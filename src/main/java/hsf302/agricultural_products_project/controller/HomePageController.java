package hsf302.agricultural_products_project.controller;


import hsf302.agricultural_products_project.model.AgriculturalProduct;
import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.service.ProductService;
import hsf302.agricultural_products_project.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomePageController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @GetMapping("/index")
    public String index(HttpSession session, Model model) {
        User account = (User) session.getAttribute("account");

        model.addAttribute("account", account);
        List<AgriculturalProduct> products = productService.getAllProducts();
        model.addAttribute("products", products);

        return "index";
    }

    @GetMapping("/about-us")
    public String aboutUs(HttpSession session, Model model) {
        User account = (User) session.getAttribute("account");
        if (account != null) {
            model.addAttribute("account", account);
            return "about_us";
        }

        return "about_us";
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }
}
