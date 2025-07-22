package hsf302.agricultural_products_project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 100, message = "Tên sản phẩm không vượt quá 100 ký tự")
    @Column(name = "name", columnDefinition = "NVARCHAR(100)", nullable = false)
    private String name;

    @NotBlank(message = "Mô tả không được để trống")
    @Size(max = 500, message = "Mô tả sản phẩm không vượt quá 500 ký tự")
    @Column(name = "description", columnDefinition = "NTEXT")
    private String description;

    @NotBlank(message = "Link ảnh không được để trống")
    @Column(name = "image_url", columnDefinition = "NVARCHAR(255)")
    private String imageUrl;

    @NotNull(message = "Giá không được để trống")
    @DecimalMin(value = "0.01", inclusive = true, message = "Giá phải lớn hơn 0")
    @Digits(integer = 13, fraction = 2, message = "Giá không hợp lệ")
    @Column(name = "price", precision = 15, scale = 2, nullable = false)
    private BigDecimal price;

    @NotNull(message = "Số lượng tồn không được để trống")
    @Min(value = 0, message = "Số lượng tồn phải là số không âm")
    @Column(name = "quantity_available", nullable = false)
    private Integer quantityAvailable;

    @OneToMany(mappedBy = "agriculturalProduct", cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "agriculturalProduct")
    private List<CartItem> cartItems;
}
