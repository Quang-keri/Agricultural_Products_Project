package hsf302.agricultural_products_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
  @GetMapping("/error-page")
    public String errorPage() {
        return "error_page";
    }
}
