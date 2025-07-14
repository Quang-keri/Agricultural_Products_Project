package hsf302.agricultural_products_project.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "order_details")
public class OrderDetail {
    @EmbeddedId
    private OrderDetailId orderDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("agriculturalProductId")
    @JoinColumn(name = "agricultural_product_id")
    private AgriculturalProduct agriculturalProduct;

    @Column(name = "quantity", nullable = false)
    private int quantity;
}
