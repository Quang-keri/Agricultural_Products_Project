//package hsf302.agricultural_products_project.shopping;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "agricututal_product")
//public class AgriculturalProduct {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "agricultural_product_id")
//    private Integer id;
//
//    @Column(name = "agricultural_product_name")
//    private String name;
//
//    @Column(name = "agricutural_product_price")
//    private double price;
//
//    @Column(name="quantity")
//    private int quantity;
//
//    @Column(name="img")
//    private String img;
//
//    public AgriculturalProduct() {
//    }
//
//    public AgriculturalProduct(int id, String name, double price, int quantity, String img) {
//        this.id = id;
//        this.name = name;
//        this.price = price;
//        this.quantity = quantity;
//        this.img = img;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public double getPrice() {
//        return price;
//    }
//
//    public void setPrice(double price) {
//        this.price = price;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }
//
//    public String getImg() {
//        return img;
//    }
//
//    public void setImg(String img) {
//        this.img = img;
//    }
//}
