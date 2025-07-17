package hsf302.agricultural_products_project.service;

import hsf302.agricultural_products_project.model.AgriculturalProduct;

import java.util.List;

public interface ProductService {
    List<AgriculturalProduct> getAllProducts();

    AgriculturalProduct saveProduct(AgriculturalProduct product);

    AgriculturalProduct getProductById(Long id);

    void updateProduct(AgriculturalProduct product);

    void deleteProduct(Long id);

    List<AgriculturalProduct> getProductsByCategory(int categoryId);

    List<AgriculturalProduct> getAllProductsById(List<Long> productIds);
}
