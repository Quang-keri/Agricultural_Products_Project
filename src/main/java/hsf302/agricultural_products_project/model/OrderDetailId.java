package hsf302.agricultural_products_project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Embeddable
public class OrderDetailId implements Serializable {
    private Long orderId;
    private Long agriculturalProductId;
}
