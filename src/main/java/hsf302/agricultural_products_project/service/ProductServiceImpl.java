package hsf302.agricultural_products_project.service;

import hsf302.agricultural_products_project.model.AgriculturalProduct;
import hsf302.agricultural_products_project.repository.AgriculturalProdcutRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private AgriculturalProdcutRepo productRepository;

    @Override
    public List<AgriculturalProduct> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public AgriculturalProduct saveProduct(AgriculturalProduct product) {
        return productRepository.save(product);
    }

    @Override
    public AgriculturalProduct getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void updateProduct(AgriculturalProduct product) {
        AgriculturalProduct existingProduct = productRepository.findById(product.getAgriculturalProductId()).orElse(null);
        if (existingProduct != null) {
            existingProduct.setDescription(product.getDescription());
            existingProduct.setImageUrl(product.getImageUrl());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setQuantityAvailable(product.getQuantityAvailable());
            productRepository.save(existingProduct);
        }
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<AgriculturalProduct> getProductsByCategory(int categoryId) {
        return productRepository.findAllByCategory_CategoryId(categoryId);
    }

    @Override
    public List<AgriculturalProduct> getAllProductsById(List<Long> productIds) {
        return productRepository.findAllById(productIds);
    }

    @Override
    public void saveAll(List<AgriculturalProduct> products) {
        productRepository.saveAll(products);
    }

    @Override
    public List<AgriculturalProduct> getProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
}
