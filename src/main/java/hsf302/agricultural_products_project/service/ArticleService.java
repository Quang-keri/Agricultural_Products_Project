package hsf302.agricultural_products_project.service;

import hsf302.agricultural_products_project.model.AgriculturalProduct;

import java.util.List;

public interface ArticleService {
    AgriculturalProduct addProduct(AgriculturalProduct aricle);
    List<AgriculturalProduct> getProducts();
    AgriculturalProduct getProductById(Long id);
}
