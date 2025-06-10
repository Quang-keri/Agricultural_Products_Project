package hsf302.agricultural_products_project.controller;

import hsf302.agricultural_products_project.dto.UserDTO;
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
    private UserService userService; // Assuming you have a UserService to handle user operations

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {

        model.addAttribute("user", new UserDTO());
        return "register";
    }

    //Bingding này ảnh xạ dựa trên thg validation của spring UserDTO có lỗi gì dồn vô thg này
    //Dùng Redirect Lưu dữ liệu tạm thời vào session,
    //  nhưng tự động xóa sau redirect xong.và nó dùng cho redirect,  model dùng trong 1 request
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserDTO userDTO,
                               BindingResult result,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        try {
            User existingUser = userService.findByUserName(userDTO.getUserName());
            if (existingUser != null) {
                result.rejectValue("userName", "error.user", "Tên đăng nhập đã tồn tại");
                return "register";
            }
            userService.save(userDTO);
            redirectAttributes.addFlashAttribute("messageRegisterSuccess", "Đăng ký thành công!,Đăng nhập để tiếp tục");
            return "redirect:/login?success";
        } catch (Exception e) {
            result.rejectValue("userName", "error.user", "Có lỗi xảy ra khi đăng ký");
            return "register";
        }
    }

}
