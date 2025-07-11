package hsf302.agricultural_products_project.dto;

import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CartCheckoutDto {
    private List<AgriculturalProductCartDto> items;
}
