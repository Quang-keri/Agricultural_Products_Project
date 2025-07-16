package hsf302.agricultural_products_project.service;

import hsf302.agricultural_products_project.model.AgriculturalProduct;
import hsf302.agricultural_products_project.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository productRepo;

    @Override
    public AgriculturalProduct addProduct(AgriculturalProduct aricle) {
        return productRepo.save(aricle);
    }

    @Override
    public List<AgriculturalProduct> getProducts() {
        return productRepo.findAll();
    }

    @Override
    public AgriculturalProduct getProductById(Long id) {
        return productRepo.findById(id).orElse(null);
    }
}
