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
                Category rauLa = new Category("Rau lá");
                Category rauCuTuoi = new Category("Rau củ tươi");
                Category nongSanKho = new Category("Nông sản khô");

                categoryService.saveAll(List.of(rauLa, rauCuTuoi, nongSanKho));

                rauLa = categoryService.findByName("Rau lá");
                rauCuTuoi = categoryService.findByName("Rau củ tươi");
                nongSanKho = categoryService.findByName("Nông sản khô");

                // Thêm sản phẩm cho từng danh mục
                List<AgriculturalProduct> rauLaProducts = List.of(
                        createProduct("Rau Muống", "Tươi xanh mỗi ngày", "https://trungtamthuocdantoc.com/wp-content/uploads/2019/10/rau-muong.jpg", new BigDecimal("8000"), 100, rauLa),
                        createProduct("Cải Ngọt", "Cải ngọt vườn nhà", "https://chotaigia.com.vn/public/images/images/san-pham/rau/cai-ngot.jpg", new BigDecimal("10000"), 80, rauLa),
                        createProduct("Rau Dền", "Mềm mịn, dễ ăn", "https://bizweb.dktcdn.net/100/382/694/products/752d07b5-29bc-4418-af7b-2d2ede0aee05.jpg?v=1675558589917", new BigDecimal("7000"), 70, rauLa),
                        createProduct("Xà Lách", "Giòn mát", "https://www.vinmec.com/static/uploads/medium_20210106_041321_793265_hat_giong_rau_xa_la_max_1800x1800_jpg_c51fb39d72.jpg", new BigDecimal("12000"), 90, rauLa)
                );

                List<AgriculturalProduct> rauCuProducts = List.of(
                        createProduct("Cà Rốt", "Ngọt, giàu Vitamin A", "https://product.hstatic.net/200000423303/product/ca-rot-huu-co_051657cb99144443bac8015f6dd34dae.jpg", new BigDecimal("15000"), 120, rauCuTuoi),
                        createProduct("Khoai Tây", "Khoai tươi, ngon", "https://bosvietnam.com/wp-content/uploads/2023/03/khoai-tay.jpg", new BigDecimal("13000"), 150, rauCuTuoi),
                        createProduct("Củ Dền", "Giàu dinh dưỡng", "https://www.vinmec.com/static/uploads/20210519_081753_529664_cho_be_an_rau_cu_de_max_1800x1800_jpg_e77bfe6a3d.jpg", new BigDecimal("17000"), 60, rauCuTuoi),
                        createProduct("Su Hào", "Tươi giòn", "https://thanhnien.mediacdn.vn/Uploaded/2014/Pictures201211/CongDong/161112/su-hao-d.jpg", new BigDecimal("11000"), 100, rauCuTuoi)
                );

                List<AgriculturalProduct> nongSanKhoProducts = List.of(
                        createProduct("Đậu xanh", "Khô, sạch", "https://bizweb.dktcdn.net/thumb/1024x1024/100/390/808/products/ddd-47ecea0a-ac73-41fa-9b47-8548e154f4c3.png?v=1592986236187", new BigDecimal("25000"), 200, nongSanKho),
                        createProduct("Nếp cái hoa vàng", "Dẻo thơm", "https://gaohungthinh.com.vn/wp-content/uploads/2023/12/Nep-cai-hoa-vang-2-scaled.jpg", new BigDecimal("35000"), 180, nongSanKho),
                        createProduct("Mè đen", "Ngon, bổ", "https://gaohungthinh.com.vn/wp-content/uploads/2023/12/Nep-cai-hoa-vang-2-scaled.jpg", new BigDecimal("22000"), 90, nongSanKho),
                        createProduct("Ngô khô", "Ngô ta phơi khô", "https://png.pngtree.com/thumb_back/fh260/background/20220227/pngtree-corn-cobs-drying-on-a-beam-closeup-market-organic-photo-image_3500785.jpg", new BigDecimal("20000"), 140, nongSanKho)
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
