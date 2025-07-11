package hsf302.agricultural_products_project.dto;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AgriculturalProductCartDto {
    private Long agriculturalProductId;
    private String name;
    private BigDecimal price;
    private String imageUrl;
    private int quantity;
}
