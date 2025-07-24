package hsf302.agricultural_products_project.controller;

import hsf302.agricultural_products_project.model.AgriculturalProduct;
import hsf302.agricultural_products_project.model.Role;
import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static hsf302.agricultural_products_project.utils.VietnameseAccentRemover.normalize;

@Controller
public class UserController {

    @Autowired
    private UserService userService;




    @GetMapping("/admin/users")
    public String listUsers(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "5") int size,
                            @RequestParam(required = false) String search,
                            Model model, HttpSession session) {
        User account = (User) session.getAttribute("account");
        model.addAttribute("user", account);

        if (account != null && account.getRole().equals(Role.ROLE_ADMIN)) {
            Page<User> userPage;

            // Nếu có từ khóa tìm kiếm
            if (search != null && !search.isEmpty()) {
                String normalizedSearch = normalize(search);
                userPage = userService.searchUsersByUserName(normalizedSearch, page, size);
                model.addAttribute("search", search); // vẫn giữ nguyên từ khóa hiển thị
            } else {
                userPage = userService.findPageUsers(page, size);
            }


            model.addAttribute("users", userPage.getContent());
            model.addAttribute("userPage", userPage);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", userPage.getTotalPages());

            return "admin/user/manageUser";
        }

        return "redirect:/403";
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
