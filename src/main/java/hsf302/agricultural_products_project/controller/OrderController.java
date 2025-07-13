package hsf302.agricultural_products_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrderController {

    @PostMapping("order/submit")
    public String createOrder() {

        return "redirect:/order-confirmation";
    }
}
