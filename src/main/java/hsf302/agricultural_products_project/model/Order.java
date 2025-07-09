package hsf302.agricultural_products_project.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderID;

    private String customerName;
    private int phoneNumber;
    private LocalDateTime date;
    private double totalPrice;
    private String statusOrder;
    private String paymentStatus;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private String address;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;
}

