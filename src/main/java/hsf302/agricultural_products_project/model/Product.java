package hsf302.agricultural_products_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    private String productID;

    private String productName;

    @Column
    private double productPrice;

    private int quantity;

    @Column(length = 150)
    private String describe;

    private String img;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;

    public Product(String productName, double productPrice, int quantity, String describe, String img, Category category, List<OrderDetail> orderDetails) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.describe = describe;
        this.img = img;
        this.category = category;
        this.orderDetails = orderDetails;
    }
}
