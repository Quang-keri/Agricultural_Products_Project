package hsf302.agricultural_products_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

    @GetMapping("/")
    //ae run project r ghi url http://localhost:8080/ z là nó chạy trang chủ nhé
    public String homePage(Model model) {
        return "index";
    }
}
