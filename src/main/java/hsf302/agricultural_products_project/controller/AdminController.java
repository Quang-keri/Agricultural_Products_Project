package hsf302.agricultural_products_project.controller;


import hsf302.agricultural_products_project.model.*;
import hsf302.agricultural_products_project.service.ArticleService;
import hsf302.agricultural_products_project.service.CategoryService;
import hsf302.agricultural_products_project.service.OrderService;
import hsf302.agricultural_products_project.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller

@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private OrderService orderService;


    @GetMapping("/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        User account = (User) session.getAttribute("account");

        if (account != null && account.getRole().equals("ROLE_ADMIN")) {
            model.addAttribute("user", account);


            return "admin/admindashboard";
        }
         return "redirect:/error-page";
    }

    @GetMapping("/users")
    public String userManagement(Model model, HttpSession session) {
        User account = (User) session.getAttribute("account");
        if (account != null && account.getRole().equals("ROLE_ADMIN")) {
            List<User> user = userService.findAll();
            model.addAttribute("users", user);
            return "admin/manageUser";
        }
        return "redirect:/error-page";
    }
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, HttpSession session) {
        User account = (User) session.getAttribute("account");
        if (account != null && account.getRole().equals("ROLE_ADMIN")) {
            User user = userService.findById(id);
            if (user != null) {
                userService.deleteById(id);
            }
            return "redirect:/admin/users";
        }
        return "redirect:/error-page";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable Long id, Model model, HttpSession session) {
        User account = (User) session.getAttribute("account");
        if (account != null && account.getRole().equals("ROLE_ADMIN")) {
            User user = userService.findById(id);
            if (user != null) {
                model.addAttribute("user", user);
                return "admin/editUser";
            }
            return "redirect:/admin/users";
        }
        return "redirect:/error-page";
    }
    @PostMapping("/users/edit/{id}")
    public String updateUser(@PathVariable Long id, User user, HttpSession session) {
        User account = (User) session.getAttribute("account");
        if (account != null && account.getRole().equals("ROLE_ADMIN")) {
            User existingUser = userService.findById(id);
            if (existingUser != null) {
                userService.updateUser(user);
            }
            return "redirect:/admin/users";
        }
        return "redirect:/error-page";
    }

    @GetMapping("/articles")
    public String productManagement(Model model,  HttpSession session) {
        User account = (User) session.getAttribute("account");
        if (account != null && account.getRole().equals("ROLE_ADMIN")) {
            List<AgriculturalProduct> articles = articleService.getProducts();
            model.addAttribute("articles", articles);
            return "admin/manageArticle";
        }
        return "redirect:/error-page";
    }
    @GetMapping("/articles/addArticle")
    public String addArticle(Model model, HttpSession session) {
        User account = (User) session.getAttribute("account");
        List<Category> categories = categoryService.getAllCategories();
        if (account != null && account.getRole().equals("ROLE_ADMIN")) {
            AgriculturalProduct article = new AgriculturalProduct();
            model.addAttribute("categories", categories);
            model.addAttribute("articles", article);
            return "admin/addArticle";
        }
        return "redirect:/error-page";
    }
    @PostMapping("/articles/addArticle")
    public String addArticle(AgriculturalProduct article, HttpSession session) {
        User account = (User) session.getAttribute("account");
        if (account != null && account.getRole().equals("ROLE_ADMIN")) {
            articleService.addProduct(article);
            return "redirect:/admin/articles";
        }
        return "redirect:/error-page";
    }

    @GetMapping("/articles/edit/{id}")
    public String editArticle(@PathVariable Long id, Model model, HttpSession session) {
        User account = (User) session.getAttribute("account");
        List<Category> categories = categoryService.getAllCategories();
        if (account != null && account.getRole().equals("ROLE_ADMIN")) {
            AgriculturalProduct article = articleService.getProductById(id);
            if (article != null) {
                model.addAttribute("categories", categories);
                model.addAttribute("articles", article);
                return "admin/editArticle";
            }
            return "redirect:/admin/articles";
        }
        return "redirect:/error-page";
    }
    @PostMapping("/articles/edit/{id}")
    public String updateArticle(@PathVariable Long id, AgriculturalProduct article, HttpSession session) {
        User account = (User) session.getAttribute("account");
        if (account != null && account.getRole().equals("ROLE_ADMIN")) {
            AgriculturalProduct existingArticle = articleService.getProductById(id);
            if (existingArticle != null) {
                articleService.addProduct(article);
            }
            return "redirect:/admin/articles";
        }
        return "redirect:/error-page";
    }

    @GetMapping("/orders")
    public String orderManagement(Model model, HttpSession session) {
        User account = (User) session.getAttribute("account");
        if (account != null && account.getRole().equals("ROLE_ADMIN")) {
            List<Order> orders = orderService.getAllOrder();
            model.addAttribute("orders", orders);
            return "admin/manageOrder";
        }
        return "redirect:/error-page";
    }


}