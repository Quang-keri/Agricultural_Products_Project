package hsf302.agricultural_products_project.service;

import hsf302.agricultural_products_project.dto.AgriculturalProductCartDto;
import hsf302.agricultural_products_project.model.User;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface CartService {
    void addToCartForCookie(String cart, Long productId, HttpServletResponse response);
    List<AgriculturalProductCartDto> getCartItemsForCookie(String cartCookie);
    void updateQuantityInCookie(String cartCookie, Long productId, int quantity, HttpServletResponse response);
    void removeFromCartForCookie(String cartCookie, Long productId, HttpServletResponse response);
    double calculateTotalPriceFromCookie(String cartCookie);
    void addToCartForUser(Long productId, User user);
}
