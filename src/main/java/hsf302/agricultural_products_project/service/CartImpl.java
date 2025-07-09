package hsf302.agricultural_products_project.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CartImpl implements  CartService {

    @Override
    public void addToCartForCookie(String cart, Long productId, HttpServletResponse response) {
        Map<Long, Integer> cartMap = new HashMap<>();
        if (!cart.isEmpty()) {
            String[] items = cart.split("\\|");
            for (String item : items) {
                String[] parts = item.split("-");
                if (parts.length == 2) {
                        cartMap.put(Long.parseLong(parts[0]), Integer.parseInt(parts[1]));
                }
            }
        }

        //Check if productId already exists in the cart + increment quantity
        cartMap.put(productId, cartMap.getOrDefault(productId, 0) + 1);

        // Write cookie
        StringBuilder newCart = new StringBuilder();
        for (Map.Entry<Long, Integer> entry : cartMap.entrySet()) {
            if (!newCart.isEmpty()) {
                newCart.append("|");
            }
            newCart.append(entry.getKey()).append("-").append(entry.getValue());
        }

        Cookie updatedCookie = new Cookie("cart", newCart.toString());
        updatedCookie.setMaxAge(7 * 24 * 60 * 60); // 7
        updatedCookie.setPath("/");
        response.addCookie(updatedCookie);
    }
}
