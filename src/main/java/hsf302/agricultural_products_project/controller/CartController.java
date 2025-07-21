package hsf302.agricultural_products_project.controller;

import hsf302.agricultural_products_project.dto.AgriculturalProductCartDto;

import hsf302.agricultural_products_project.dto.CustomerOrderDto;
import hsf302.agricultural_products_project.model.PaymentStatus;
import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.service.CartService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

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
    return "redirect:/product/all-product";
    }
    @GetMapping("/cart")
    public String viewCart(HttpSession session, @CookieValue(value = "cart", defaultValue = "") String cartCookie, Model model) {
        User account = (User) session.getAttribute("account");

        if (account == null) {
            List<AgriculturalProductCartDto> cartItems = cartService.getCartItemsForCookie(cartCookie);
            double totalPrice = cartService.calculateTotalPriceFromCookie(cartCookie);


            CustomerOrderDto checkoutDto = CustomerOrderDto.builder()
                    .items(cartItems)
                    .total(totalPrice)
                    .build();
            model.addAttribute("checkoutDto", checkoutDto);
        }else {
            List<AgriculturalProductCartDto> cartItems = cartService.getCartItemsForUser(account);
           // model.addAttribute("cart", cartItems);
            double totalPrice = cartService.calculateTotalPriceFromUser(account);

            // Add command object for form binding
            CustomerOrderDto checkoutDto = CustomerOrderDto.builder()
                .items(cartItems)
                .total(totalPrice)
                .build();
            model.addAttribute("checkoutDto", checkoutDto);
            model.addAttribute("account", account);
        }

        return "cart";
    }

    @GetMapping("/cart/checkout")
    public String checkout(@ModelAttribute CustomerOrderDto checkoutDto,
                           HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        User account = (User) session.getAttribute("account");

        if (account == null) {
            return "redirect:/login";
        }
        CustomerOrderDto orderCheckout = null;
        if(checkoutDto==null || checkoutDto.getItems() == null || checkoutDto.getItems().isEmpty()) {
            orderCheckout = (CustomerOrderDto) session.getAttribute("orderCheckout");
        }else{

            Map<String, String> stockErrors = cartService.checkStockDetails(checkoutDto.getItems());
            if (!stockErrors.isEmpty()) {
                redirectAttributes.addFlashAttribute("stockErrors", stockErrors);
                return "redirect:/cart";
            }

            orderCheckout = CustomerOrderDto.builder()
                    .name(account.getUserName())
                    .phoneNumber(account.getPhoneNumber())
                    .address(account.getAddress())
                    .total(checkoutDto.getTotal())
                    .paymentStatus(PaymentStatus.PENDING)
                    .paymentMethod("COD") // Default payment method
                    .items(checkoutDto.getItems())
                    .build();
            session.setAttribute("orderCheckout", orderCheckout);
        }

        // Get the items from the wrapper object
        model.addAttribute("orderCheckout", orderCheckout);
        model.addAttribute("account", account);

        // Calculate total
//        double total = items.stream()
//            .mapToDouble(item -> item.getPrice().doubleValue() * item.getQuantity())
//            .sum();
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

