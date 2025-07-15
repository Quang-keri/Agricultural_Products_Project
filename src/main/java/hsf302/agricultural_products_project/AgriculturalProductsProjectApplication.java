package hsf302.agricultural_products_project;

import hsf302.agricultural_products_project.model.Role;
import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.service.UserService;
import hsf302.agricultural_products_project.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AgriculturalProductsProjectApplication {
    @Autowired
    private UserService userService;
    public static void main(String[] args) {
        SpringApplication.run(AgriculturalProductsProjectApplication.class, args);
    }
    @Bean
    public CommandLineRunner initAdmin() {
        return args -> {
            // Check nếu chưa có tài khoản admin
            if (userService.findByUserName("admin") == null) {
                User admin = new User();
                admin.setUserName("admin");
                admin.setPassword(PasswordUtils.hashPassword("12345678"));
                admin.setRole(Role.ROLE_ADMIN);
                admin.setUserFullName("Administrator");
                admin.setPhoneNumber("0123456789");
                admin.setAddress("Admin Address");
                admin.setStatus(true);

                userService.save(admin);
                System.out.println("Admin account created with username = admin, password = 12345678");
            } else {
                System.out.println("Admin account already exists.");
            }
        };
    }
}
