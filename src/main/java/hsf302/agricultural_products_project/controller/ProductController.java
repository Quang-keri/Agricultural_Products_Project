package hsf302.agricultural_products_project.controller;

import hsf302.agricultural_products_project.model.AgriculturalProduct;
import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/product-detail")
    public String showProductDetail() {
        return "product-detail"; // Trả về tên file .html trong /templates
    }

    @GetMapping("/products")
    public String showProductList(Model model, HttpSession session) {
        User account = (User) session.getAttribute("account");
        List<AgriculturalProduct> productList = productService.getProducts();
        model.addAttribute("productList", productList); // Thêm danh sách sản phẩm vào mô hình
        if (account != null) {
            model.addAttribute("account", account); // Thêm thông tin tài khoản vào mô hình nếu người dùng đã đăng nhập
        }
        return "product-list"; // Trả về tên file .html trong /templates
    }
}
