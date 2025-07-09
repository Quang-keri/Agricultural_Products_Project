package hsf302.agricultural_products_project.controller;

import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.service.CartService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;
    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam Long productId, HttpSession session, @CookieValue(value = "cart", defaultValue = "") String cartCookie, HttpServletResponse response) {

    User account = (User) session.getAttribute("account");
    if (account == null) {
        cartService.addToCartForCookie(cartCookie, productId, response);
    }
    return "redirect:/products"; // Trả về trang danh sách sản phẩm sau khi thêm vào giỏ hàng
    }
}
