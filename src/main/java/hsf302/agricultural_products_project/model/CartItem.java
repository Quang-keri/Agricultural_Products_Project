package hsf302.agricultural_products_project.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Generated
@Entity
@Table(name = "cart_items")
public class CartItem {
    @EmbeddedId
    private CartItemId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("cartId")
    @JoinColumn(name = "cart_id", referencedColumnName = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("agriculturalProductId")
    @JoinColumn(name = "agricultural_product_id", referencedColumnName = "agricultural_id")
    private AgriculturalProduct agriculturalProduct;

    @Column(name = "quantity", nullable = false)
    private int quantity;
}
