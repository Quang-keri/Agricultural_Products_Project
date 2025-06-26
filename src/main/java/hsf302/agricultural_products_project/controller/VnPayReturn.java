package hsf302.agricultural_products_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class VnPayReturn {
    @GetMapping("/vnpayReturn")
    public String vnpayReturn() {



        // Trả về trang thông báo thanh toán thành công
        return "paymentResult"; // Tên file HTML trong thư mục templates
    }
}
