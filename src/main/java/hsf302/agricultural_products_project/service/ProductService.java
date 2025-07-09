package hsf302.agricultural_products_project.service;

import hsf302.agricultural_products_project.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product saveProduct(Product product);
    Product getProductById(Long id);
    void deleteProduct(Long id);
    List<Product> getProductsByCategory(int categoryId);
}
