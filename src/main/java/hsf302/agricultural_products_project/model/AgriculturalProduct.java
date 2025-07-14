package hsf302.agricultural_products_project.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "agricultural_products")
public class AgriculturalProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agricultural_product_id")
    private Long agriculturalProductId;

    @Column(name = "name", columnDefinition = "NVARCHAR(100)", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "NTEXT")
    private String description;

    @Column(name = "image_url", columnDefinition = "NVARCHAR(255)")
    private String imageUrl;

    @Column(name = "price", precision = 15, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "quantity_available", nullable = false)
    private int quantityAvailable;

    @OneToMany(mappedBy = "agriculturalProduct", cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetail;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "agriculturalProduct")
    private List<CartItem> cartItems;
}
