package hsf302.agricultural_products_project.controller;


import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;


    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }
    @PostMapping("/login")
    public String loginPage(@RequestParam String username,
                            @RequestParam String password,
                            Model model, HttpSession session) {

        User acc = userService.findByUserName(username);

        if (acc != null && acc.getPassword().equals(password)) {
            session.setAttribute("acc", acc);

            if(acc.getRole().equals("ROE_ADMIN")){
                return "redirect:/admin/admindashboard";
            }else if(acc.getRole().equals("ROLE_MEMBER")) {
                return "redirect:/index";
            }else {
                return "redirect:/index";
            }
        }else{
            model.addAttribute("errorMessage", "Sai tên đăng nhập hoặc mật khẩu!");
        }
        return "login";
    }
}
