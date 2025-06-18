package hsf302.agricultural_products_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    @GetMapping("/product-detail")
    public String showProductDetail() {
        return "product-detail"; // Trả về tên file .html trong /templates
    }
}
