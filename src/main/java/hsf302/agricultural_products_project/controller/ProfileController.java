package hsf302.agricultural_products_project.controller;

import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;


    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        User account = (User) session.getAttribute("account");
        if(account != null){
            model.addAttribute("account", account);
            return "profile";
        }
      return "redirect:/login";
    }


    @GetMapping("/update/{id}")
    public String updateProfile(HttpSession session, Model model,@PathVariable int id) {
        return "profile-update";
    }

    @PostMapping("/update")
    public  String updateProfile(HttpSession session, Model model,User user) {
        User account = (User) session.getAttribute("user");
        if(account != null){
//            userService.updateProfile(account.getId(), user);

            model.addAttribute("account", account);
            return "profile";
        }
        return "redirect:/login";
    }

}
