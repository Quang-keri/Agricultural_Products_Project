package hsf302.agricultural_products_project.dto;

import hsf302.agricultural_products_project.model.PaymentStatus;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CustomerOrderDto {
    private String name;
    private String phoneNumber;
    private String address;
    private PaymentStatus paymentStatus;
    private double total;
    List<AgriculturalProductCartDto> items;
}
