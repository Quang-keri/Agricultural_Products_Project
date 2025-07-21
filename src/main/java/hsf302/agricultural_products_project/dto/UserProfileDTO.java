package hsf302.agricultural_products_project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserProfileDTO {
    @NotNull(message = "ID người dùng không được để trống")
    private Long userId;

    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(min = 4,max = 20,message = "Tên dăng nhập ít nhất 4 và nhiều nhất 20 kí tự")
    private String userName;

    @NotBlank(message = "Họ tên không được để trống")
    @Size(min = 3,max =50,message = "Tên đầy đủ ít nhất 3 và nhiều nhất 50 k tự")
    private String userFullName;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 8,message = "Mật khẩu tối đa là 8 kí tự")
    private String password;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String address;

    @NotBlank(message = "Số điện thoại không để trống ")
    @Pattern(regexp = "\\d{10}", message = "số điện thoại phải 10 số")
    private String phoneNumber;


    private String role;


    private String status;

    public UserProfileDTO() {
    }

    public UserProfileDTO(Long userId, String userName, String userFullName, String password, String address, String phoneNumber, String role, String status) {
        this.userId = userId;
        this.userName = userName;
        this.userFullName = userFullName;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
