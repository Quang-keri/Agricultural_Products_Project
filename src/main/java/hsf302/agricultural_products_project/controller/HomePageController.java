package hsf302.agricultural_products_project.controller;

import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.service.CustomUserDetails;
import hsf302.agricultural_products_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {
    @Autowired
    private UserService userService;
    @GetMapping("/index")
    //ae run project r ghi url http://localhost:8080/ z là nó chạy trang chủ nhé
    public String index(Model model) {
        // dòng này lấy thông tin người dùng đã đăng nhập từ SecuritySecurity qua cách  gọi SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof CustomUserDetails) {

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();
            System.out.println("User: " + user.getUserName() + ", Role: " + user.getRole());
            model.addAttribute("user", user);
        }

        return "index";
    }

}
