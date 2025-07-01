package hsf302.agricultural_products_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_details")
public class OrderDetail {
    @EmbeddedId
    private OrderDetailId orderDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("agriculturalId")
    @JoinColumn(name = "agricultural_id", referencedColumnName = "agricultural_id")
    private AgriculturalProduct agriculturalProduct;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", precision = 15, scale = 2, nullable = false)
    private BigDecimal price;
}
