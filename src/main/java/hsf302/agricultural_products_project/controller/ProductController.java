package hsf302.agricultural_products_project.controller;

import hsf302.agricultural_products_project.model.AgriculturalProduct;
import hsf302.agricultural_products_project.service.ProductService;
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

    @GetMapping("/product-list")
    public String showProductList(Model model) {
        List<AgriculturalProduct> productList = productService.getProducts();
        model.addAttribute("productList", productList); // Thêm danh sách sản phẩm vào mô hình
        return "product-list"; // Trả về tên file .html trong /templates
    }
}
