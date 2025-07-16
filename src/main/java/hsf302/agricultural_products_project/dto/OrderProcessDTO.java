package hsf302.agricultural_products_project.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderProcessDTO {
    private Long orderID;
    private Long userID;
    private String customerName;
    private String phoneNumber;
    private LocalDateTime date;
    private BigDecimal totalPrice;
    private String statusOrder;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private String address;
}
