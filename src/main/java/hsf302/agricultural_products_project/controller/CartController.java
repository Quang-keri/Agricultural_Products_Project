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
    public String addToCart(Model model,@RequestParam Long productId, HttpSession session, @CookieValue(value = "cart", defaultValue = "") String cartCookie, HttpServletResponse response) {

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
            List<AgriculturalProductCartDto> cartItems = cartService.getCartItemsForUser(account);
            model.addAttribute("cart", cartItems);
            double totalPrice = cartService.calculateTotalPriceFromUser(account);
            model.addAttribute("totalPrice", totalPrice);

            // Add command object for form binding
            CartCheckoutDto checkoutDto = new CartCheckoutDto(cartItems);
            model.addAttribute("checkoutDto", checkoutDto);
            model.addAttribute("account", account);
        }

        return "cart";
    }

    @GetMapping("/cart/checkout")
    public String checkout(@ModelAttribute CartCheckoutDto checkoutDto,
                          HttpSession session, Model model) {
        User account = (User) session.getAttribute("account");

        if (account == null) {
            return "redirect:/login";
        }
        List<AgriculturalProductCartDto> items = null;
        if(checkoutDto==null || checkoutDto.getItems() == null || checkoutDto.getItems().isEmpty()) {
            items = ( List<AgriculturalProductCartDto>) session.getAttribute("cart");
        }else{
            items = checkoutDto.getItems();
            session.setAttribute("cart", items);
        }

        // Get the items from the wrapper object
        model.addAttribute("cartItems", items);
        model.addAttribute("account", account);

        // Calculate total
        double total = items.stream()
            .mapToDouble(item -> item.getPrice().doubleValue() * item.getQuantity())
            .sum();
        model.addAttribute("total", total);
        model.addAttribute("account", account);
        return "order";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Long id, HttpSession session,
                                @CookieValue(value = "cart", defaultValue = "") String cartCookie,
                                HttpServletResponse response) {
        User account = (User) session.getAttribute("account");
        if (account == null) {
            cartService.removeFromCartForCookie(cartCookie, id, response);
        }else{
            cartService.removeFromCartForUser(id, account);
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
                cartService.updateQuantityInUserCart(productId, quantity, account);
            }
        }

        return "redirect:/cart";
    }
}

