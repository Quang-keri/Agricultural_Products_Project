package hsf302.agricultural_products_project.service;

import hsf302.agricultural_products_project.model.AgriculturalProduct;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    List<AgriculturalProduct> getAllProducts();

    AgriculturalProduct saveProduct(AgriculturalProduct product);

    AgriculturalProduct getProductById(Long id);

    void updateProduct(AgriculturalProduct product);

    void deleteProduct(Long id);

    List<AgriculturalProduct> getProductsByCategory(int categoryId);

    List<AgriculturalProduct> getAllProductsById(List<Long> productIds);
    long countProducts();
    void saveAll(List<AgriculturalProduct> products);

    List<AgriculturalProduct> getProductsByName(String name);
    Page<AgriculturalProduct> getAllProduct(int page);

    Page<AgriculturalProduct> getProductsByName(String name, int page);
}
