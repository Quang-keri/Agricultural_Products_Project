package hsf302.agricultural_products_project.service;

import hsf302.agricultural_products_project.model.AgriculturalProduct;

import java.util.List;

public interface ProductService {
    List<AgriculturalProduct> getProducts();
    List<AgriculturalProduct> getAllProductsById(List<Long> ids);
}
