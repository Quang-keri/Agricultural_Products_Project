package hsf302.agricultural_products_project.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name",unique = true, length = 50)
    private String userName;

    @Column(name = "full_name", length = 100)
    private String userFullName;

    @Column(name = "password")
    @Size(min = 8, message = "Mật khẩu ít nhất 8 ký tự")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "Password must be at least 8 characters and include uppercase, lowercase, and a number")
    @NotBlank(message = "Password is required")
    private  String password;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "status")
    private String status;

    @Column(name = "phone_number")
    @Pattern(
            regexp = "^(0)(3[2-9]|5[6|8|9]|7[0|6-9]|8[1-5]|9[0-9])[0-9]{7}$",
            message = "Số điện thoại không hợp lệ"
    )
    private String phoneNumber;

    private String role;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<Order> orders;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Article> articles;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "user")
    private Cart cart;
}
