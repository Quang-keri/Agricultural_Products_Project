package hsf302.agricultural_products_project.controller;


import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.service.UserService;
import hsf302.agricultural_products_project.utils.PasswordUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;


    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {

        User account = userService.findByUserName(username);
        if(account == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không đúng");
            return "redirect:/login";
        }

        boolean checkPass = PasswordUtils.verifyPassword(password, account.getPassword());
        if(!checkPass) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không đúng");
            return "redirect:/login";
        }

        if (account.getRole().equals("ROLE_ADMIN")) {
            session.setAttribute("account", account);
            session.setMaxInactiveInterval(24*60 * 60);
            return "redirect:/admin/dashboard";
        } else if (account.getRole().equals("ROLE_MEMBER")) {
            session.setAttribute("account", account);
            session.setMaxInactiveInterval(24*60 * 60);
            return "redirect:/index";
        }

        redirectAttributes.addFlashAttribute("errorMessage", "Vai trò không hợp lệ");
        return "redirect:/login";
    }

}
