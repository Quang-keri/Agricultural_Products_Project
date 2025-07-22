package hsf302.agricultural_products_project.controller;

import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user/form";
    }

    @GetMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.updateStatus(id);
        return "redirect:/admin/users";
    }

//        @GetMapping("/users")
//    public String userManagement(Model model) {
//        model.addAttribute("users", userService.findAll());
//        return "admin/manageUser";
//    }


//       @GetMapping("/page/{pageNo}")
//    public String findPaginated(@PathVariable (value = "pageNo") int pageNo,Model model ){
//        int pageSize = 5; // có thể change số bất kì
//        Page<Employee> page = employeeService.findpaginated(pageNo, pageSize);
//        List<Employee> listEmployees = page.getContent();
//        model.addAttribute("currentPage",pageNo);
//        model.addAttribute("totalPages",page.getTotalPages());
//        model.addAttribute("totalItems", page.getTotalElements());
//        model.addAttribute("listEmployees",listEmployees);
//        return  "index";
//
//    }
}
