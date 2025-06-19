//package hsf302.agricultural_products_project.controller;

//import hsf302.agricultural_products_project.shopping.AgriculturalProduct;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.List;
//
//@Controller
//public class ViewShopController {

//    @GetMapping("/trang-san-pham")
//    public String trangSanPham(Model model) {
//        List<AgriculturalProduct> products = List.of(
//                new AgriculturalProduct("Laptop Asus", 15000000, "", "Mạnh mẽ, bền bỉ"),
//                new AgriculturalProduct("MacBook Air", 25000000, "", "Gọn nhẹ, cao cấp")
//        );
//        model.addAttribute("products", products);
//        return "viewCart";
//    }
//}
