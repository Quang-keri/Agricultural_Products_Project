package hsf302.agricultural_products_project.service;

import jakarta.servlet.http.HttpServletResponse;

public interface CartService {
    void addToCartForCookie(String cart, Long productId, HttpServletResponse response);
}
