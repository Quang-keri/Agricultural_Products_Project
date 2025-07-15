package hsf302.agricultural_products_project.controller;

import hsf302.agricultural_products_project.dto.UserDTO;
import hsf302.agricultural_products_project.model.Role;
import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {
@Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {

        model.addAttribute("user", new UserDTO());
        return "register";
    }


    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserDTO userDTO,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        try {
            User existingUser = userService.findByUserName(userDTO.getUserName());
            if (existingUser != null) {
                model.addAttribute("UserNameExist","Tên đăng nhập đã tồn tại,vui lòng chọn tên khác");
                return "register";
            }
            userDTO.setRole("ROLE_MEMBER");
            userService.save(userDTO);
            model.addAttribute("messageRegisterSuccess", "Đăng ký thành công!,Đăng nhập để tiếp tục");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("messageRegisterError","Đăng ký không thành công, vui lòng thử lại sau");
            return "register";
        }
    }

}
