package hsf302.agricultural_products_project.controller;

import hsf302.agricultural_products_project.dto.OrderDetailDTO;
import hsf302.agricultural_products_project.dto.OrderProcessDTO;
import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.service.OrderDetailService;
import hsf302.agricultural_products_project.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/orderManagement/detail/{orderId}")
    public String showOrderDetailFromManagement(@PathVariable("orderId") int orderId,
                                                HttpSession session,
                                                Model model) {
        User account = (User) session.getAttribute("account");
        if (account == null) {
            return "redirect:/login";
        }

        List<OrderDetailDTO> orderDetails = orderDetailService.getOrderDetailDTOsByOrderId(orderId);
        List<OrderProcessDTO> orders = orderService.getOrderManagement(account.getUserId());

        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("orders", orders);
        model.addAttribute("showDetailModal", true);
        model.addAttribute("orderId", orderId);
        model.addAttribute("account", account);

        return "orderManagement";
    }

    @GetMapping("/history/detail/{orderId}")
    public String showOrderDetailFromHistory(@PathVariable("orderId") int orderId,
                                             HttpSession session,
                                             Model model) {
        User account = (User) session.getAttribute("account");
        if (account == null) {
            return "redirect:/login";
        }

        List<OrderDetailDTO> orderDetails = orderDetailService.getOrderDetailDTOsByOrderId(orderId);
        List<OrderProcessDTO> historyOrders = orderService.getOrderHistory(account.getUserId());

        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("orders", historyOrders);
        model.addAttribute("showDetailModal", true);
        model.addAttribute("orderId", orderId);
        model.addAttribute("account", account);

        return "history";
    }



}
