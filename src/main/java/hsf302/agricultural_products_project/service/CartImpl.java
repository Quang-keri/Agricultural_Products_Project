package hsf302.agricultural_products_project.service;

import hsf302.agricultural_products_project.dto.AgriculturalProductCartDto;
import hsf302.agricultural_products_project.model.*;
import hsf302.agricultural_products_project.repository.AgriculturalProdcutRepo;
import hsf302.agricultural_products_project.repository.CartRepo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CartImpl implements  CartService {
    @Autowired
    private CartRepo cartRepository;

    @Autowired
    private AgriculturalProdcutRepo agriculturalProductRepository;
    @Override
    public void addToCartForCookie(String cart, Long productId, HttpServletResponse response) {
        Map<Long, Integer> cartMap = getLongIntegerMap(cart);


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

    private Map<Long, Integer> getLongIntegerMap(String cart) {
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
        return cartMap;
    }

    @Override
    public List<AgriculturalProductCartDto> getCartItemsForCookie(String cartCookie) {
        Map<Long, Integer> cartMap = getLongIntegerMap(cartCookie);
        List<AgriculturalProductCartDto> cartItems = new ArrayList<>();
        for( Map.Entry<Long, Integer> entry : cartMap.entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();
            AgriculturalProduct product = agriculturalProductRepository.findById(productId).orElse(null);
            if (product != null) {
                AgriculturalProductCartDto cartRes = AgriculturalProductCartDto.builder()
                        .agriculturalProductId(productId)
                        .name(product.getName())
                        .price(product.getPrice())
                        .imageUrl(product.getImageUrl())
                        .quantity(quantity)
                        .build();
                cartItems.add(cartRes);
            }
        }
        return cartItems;
    }

    @Override
    public void updateQuantityInCookie(String cartCookie, Long productId, int quantity, HttpServletResponse response) {
        Map<Long, Integer> cartMap = getLongIntegerMap(cartCookie);

        if (quantity <= 0) {
            cartMap.remove(productId);
        } else {
            cartMap.put(productId, quantity);
        }

        // Write updated cart to cookie
        StringBuilder newCart = new StringBuilder();
        for (Map.Entry<Long, Integer> entry : cartMap.entrySet()) {
            if (!newCart.isEmpty()) {
                newCart.append("|");
            }
            newCart.append(entry.getKey()).append("-").append(entry.getValue());
        }

        Cookie updatedCookie = new Cookie("cart", newCart.toString());
        updatedCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
        updatedCookie.setPath("/");
        response.addCookie(updatedCookie);
    }

    @Override
    public void removeFromCartForCookie(String cartCookie, Long productId, HttpServletResponse response) {
        Map<Long, Integer> cartMap = getLongIntegerMap(cartCookie);
        cartMap.remove(productId);

        // Write updated cart to cookie
        StringBuilder newCart = new StringBuilder();
        for (Map.Entry<Long, Integer> entry : cartMap.entrySet()) {
            if (!newCart.isEmpty()) {
                newCart.append("|");
            }
            newCart.append(entry.getKey()).append("-").append(entry.getValue());
        }

        Cookie updatedCookie = new Cookie("cart", newCart.toString());
        updatedCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
        updatedCookie.setPath("/");
        response.addCookie(updatedCookie);
    }

    @Override
    public double calculateTotalPriceFromCookie(String cartCookie) {
        Map<Long, Integer> cartMap = getLongIntegerMap(cartCookie);
        double totalPrice = 0.0;

        for (Map.Entry<Long, Integer> entry : cartMap.entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();

            AgriculturalProduct product = agriculturalProductRepository.findById(productId).orElse(null);
            if (product != null) {
                // Convert BigDecimal to double and multiply with quantity
                double itemPrice = product.getPrice().doubleValue();
                totalPrice += itemPrice * quantity;
            }
        }

        return totalPrice;
    }

    @Override
    @Transactional
    public void addToCartForUser(Long productId, User user) {
        Optional<Cart> existingCart = cartRepository.findByUser(user);
        AgriculturalProduct product = agriculturalProductRepository.findById(productId).orElse(null);
        if(!existingCart.isPresent()){
            Cart newCart = cartRepository.save(Cart.builder()
                    .user(user)
                    .build());
            if (product != null) {
                CartItemId cartItemId = new CartItemId();
                cartItemId.setCartId(newCart.getCartId());
                cartItemId.setAgriculturalProductId(product.getId());
                CartItem cartItem = CartItem.builder()
                        .id(cartItemId)
                        .cart(newCart)
                        .agriculturalProduct(product)
                        .quantity(1) // Default quantity is 1
                        .build();
                newCart.getCartItems().add(cartItem);
                cartRepository.save(newCart);
            }
        }else{
            for( CartItem item : existingCart.get().getCartItems()) {
                if (item.getId().getAgriculturalProductId().equals(productId) ) {
                    // Product already exists in the cart, increment quantity
                    item.setQuantity(item.getQuantity() + 1);
                    cartRepository.save(existingCart.get());
                    return;
                }
            }
            // Product does not exist in the cart, add new item
            CartItemId cartItemId = new CartItemId();
            cartItemId.setCartId(existingCart.get().getCartId());
            cartItemId.setAgriculturalProductId(product.getId());
            CartItem cartItem = CartItem.builder()
                    .id(cartItemId)
                    .cart(existingCart.get())
                    .agriculturalProduct(product)
                    .quantity(1) // Default quantity is 1
                    .build();
            existingCart.get().getCartItems().add(cartItem);
            cartRepository.save(existingCart.get());
        }
    }
}
