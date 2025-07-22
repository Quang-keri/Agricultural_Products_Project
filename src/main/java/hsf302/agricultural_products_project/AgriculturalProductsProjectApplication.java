package hsf302.agricultural_products_project;

import hsf302.agricultural_products_project.model.AgriculturalProduct;
import hsf302.agricultural_products_project.model.Category;
import hsf302.agricultural_products_project.model.Role;
import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.service.CategoryService;
import hsf302.agricultural_products_project.service.ProductService;
import hsf302.agricultural_products_project.service.UserService;
import hsf302.agricultural_products_project.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.List;

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

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            if (categoryService.findByName("Rau lá") == null) {
                // Tạo các danh mục
                Category rauLa = new Category(null, "Rau lá", "Bài viết về rau lá", null, null);
                Category rauCuTuoi = new Category(null, "Rau củ tươi", "Bài viết về rau củ tươi", null, null);
                Category nongSanKho = new Category(null, "Nông sản khô", "Bài viết về nông sản khô", null, null);

                categoryService.saveAll(List.of(rauLa, rauCuTuoi, nongSanKho));

                rauLa = categoryService.findByName("Rau lá");
                rauCuTuoi = categoryService.findByName("Rau củ tươi");
                nongSanKho = categoryService.findByName("Nông sản khô");

                // Thêm sản phẩm cho từng danh mục
                List<AgriculturalProduct> rauLaProducts = List.of(
                        createProduct("Rau Muống", "Tươi xanh mỗi ngày", "https://example.com/rau_muong.jpg", new BigDecimal("8000"), 100, rauLa),
                        createProduct("Cải Ngọt", "Cải ngọt vườn nhà", "https://example.com/cai_ngot.jpg", new BigDecimal("10000"), 80, rauLa),
                        createProduct("Rau Dền", "Mềm mịn, dễ ăn", "https://example.com/rau_den.jpg", new BigDecimal("7000"), 70, rauLa),
                        createProduct("Xà Lách", "Giòn mát", "https://example.com/xa_lach.jpg", new BigDecimal("12000"), 90, rauLa)
                );

                List<AgriculturalProduct> rauCuProducts = List.of(
                        createProduct("Cà Rốt", "Ngọt, giàu Vitamin A", "https://example.com/ca_rot.jpg", new BigDecimal("15000"), 120, rauCuTuoi),
                        createProduct("Khoai Tây", "Khoai tươi, ngon", "https://example.com/khoai_tay.jpg", new BigDecimal("13000"), 150, rauCuTuoi),
                        createProduct("Củ Dền", "Giàu dinh dưỡng", "https://example.com/cu_den.jpg", new BigDecimal("17000"), 60, rauCuTuoi),
                        createProduct("Su Hào", "Tươi giòn", "https://example.com/su_hao.jpg", new BigDecimal("11000"), 100, rauCuTuoi)
                );

                List<AgriculturalProduct> nongSanKhoProducts = List.of(
                        createProduct("Đậu xanh", "Khô, sạch", "https://example.com/dau_xanh.jpg", new BigDecimal("25000"), 200, nongSanKho),
                        createProduct("Nếp cái hoa vàng", "Dẻo thơm", "https://example.com/nep.jpg", new BigDecimal("35000"), 180, nongSanKho),
                        createProduct("Mè đen", "Ngon, bổ", "https://example.com/me_den.jpg", new BigDecimal("22000"), 90, nongSanKho),
                        createProduct("Ngô khô", "Ngô ta phơi khô", "https://example.com/ngo_kho.jpg", new BigDecimal("20000"), 140, nongSanKho)
                );

                productService.saveAll(rauLaProducts);
                productService.saveAll(rauCuProducts);
                productService.saveAll(nongSanKhoProducts);

                System.out.println("Đã khởi tạo danh mục và sản phẩm mẫu thành công.");
            } else {
                System.out.println("Dữ liệu mẫu đã tồn tại.");
            }
        };
    }

    private AgriculturalProduct createProduct(String name, String desc, String imgUrl, BigDecimal price, int qty, Category category) {
        return AgriculturalProduct.builder()
                .name(name)
                .description(desc)
                .imageUrl(imgUrl)
                .price(price)
                .quantityAvailable(qty)
                .category(category)
                .build();
    }

}
