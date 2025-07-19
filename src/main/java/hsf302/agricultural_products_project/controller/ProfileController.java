package hsf302.agricultural_products_project.controller;

import hsf302.agricultural_products_project.dto.UserDTO;
import hsf302.agricultural_products_project.dto.UserProfileDTO;
import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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




    @PostMapping("/profile/update")
    public  String updateProfile(@Valid @ModelAttribute("dto") UserProfileDTO user, BindingResult result,HttpSession session, Model model) {
        User account = (User) session.getAttribute("account");
        if(account != null){
            if(result.hasErrors()){
                model.addAttribute("account", account);
                return "profile-update";
            }
            User updatedAccount = userService.findById(account.getUserId());
            session.setAttribute("account", updatedAccount);
           userService.updateProfile(user);
            model.addAttribute("account", updatedAccount);
            return "profile";
        }
        return "redirect:/login";
    }


    @GetMapping("/profile/update")
    public String updatepProfile(HttpSession session, Model model) {
        User account = (User) session.getAttribute("account");
        if (account != null) {
            UserProfileDTO dto = new UserProfileDTO();
            dto.setUserId(account.getUserId());
            dto.setUserName(account.getUserName());
            dto.setUserFullName(account.getUserFullName());
            dto.setPassword(account.getPassword());
            dto.setAddress(account.getAddress());
            dto.setPhoneNumber(account.getPhoneNumber());

            model.addAttribute("dto", dto);
            return "profile-update";
        }
        return "redirect:/login";
    }
}
