package hsf302.agricultural_products_project.controller;

import hsf302.agricultural_products_project.dto.AgriculturalProductCartDto;
import hsf302.agricultural_products_project.dto.CartCheckoutDto;
import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.service.CartService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;
    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam Long productId, HttpSession session, @CookieValue(value = "cart", defaultValue = "") String cartCookie, HttpServletResponse response) {

    User account = (User) session.getAttribute("account");
    if (account == null) {
        cartService.addToCartForCookie(cartCookie, productId, response);
    }else{
        cartService.addToCartForUser(productId, account);
    }
    return "redirect:/products"; // Trả về trang danh sách sản phẩm sau khi thêm vào giỏ hàng
    }
    @GetMapping("/cart")
    public String viewCart(HttpSession session, @CookieValue(value = "cart", defaultValue = "") String cartCookie, Model model) {
        User account = (User) session.getAttribute("account");
        // Check if the user is logged in
        if (account == null) {
            List<AgriculturalProductCartDto> cartItems = cartService.getCartItemsForCookie(cartCookie);
            model.addAttribute("cart", cartItems);
            double totalPrice = cartService.calculateTotalPriceFromCookie(cartCookie);
            model.addAttribute("totalPrice", totalPrice);

            // Add command object for form binding
            CartCheckoutDto checkoutDto = new CartCheckoutDto(cartItems);
            model.addAttribute("checkoutDto", checkoutDto);
        }else {

        }

        return "cart";
    }

    @PostMapping("/cart/checkout")
    public String checkout(@ModelAttribute CartCheckoutDto checkoutDto,
                          HttpSession session, Model model) {
        User account = (User) session.getAttribute("account");

        if (account == null) {
            return "redirect:/login";
        }

        // Get the items from the wrapper object
        List<AgriculturalProductCartDto> items = checkoutDto.getItems();
        model.addAttribute("cartItems", items);
        model.addAttribute("account", account);

        // Calculate total
        double total = items.stream()
            .mapToDouble(item -> item.getPrice().doubleValue() * item.getQuantity())
            .sum();
        model.addAttribute("total", total);

        return "payment-form";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Long id, HttpSession session,
                                @CookieValue(value = "cart", defaultValue = "") String cartCookie,
                                HttpServletResponse response) {
        User account = (User) session.getAttribute("account");
        if (account == null) {
            cartService.removeFromCartForCookie(cartCookie, id, response);
        }
        return "redirect:/cart";
    }

    @PostMapping("/cart/update-quantity")
    public String updateQuantity(@RequestParam Long productId,
                                @RequestParam int quantity,
                                HttpSession session,
                                @CookieValue(value = "cart", defaultValue = "") String cartCookie,
                                HttpServletResponse response) {
        User account = (User) session.getAttribute("account");

        if (quantity >= 1) {
            if (account == null) {
                // Update cart in cookie for non-logged-in users
                cartService.updateQuantityInCookie(cartCookie, productId, quantity, response);
            } else {
                // Update cart in database for logged-in users
                // You can implement this method in your CartService
                // cartService.updateQuantityForUser(account.getId(), productId, quantity);
            }
        }

        return "redirect:/cart";
    }
}

