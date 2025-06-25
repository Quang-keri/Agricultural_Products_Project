package hsf302.agricultural_products_project.controller;



import hsf302.agricultural_products_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {
    @Autowired
    private UserService userService;
    @GetMapping("/index")
    //ae run project r ghi url http://localhost:8333/ z là nó chạy trang chủ nhé
    public String home(Model model) {


        return "index";
    }

}
