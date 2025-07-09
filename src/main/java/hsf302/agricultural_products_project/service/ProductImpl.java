package hsf302.agricultural_products_project.service;

import hsf302.agricultural_products_project.model.AgriculturalProduct;
import hsf302.agricultural_products_project.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImpl implements  ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Override
    public List<AgriculturalProduct> getProducts() {
        return productRepo.findAll();
    }
}
