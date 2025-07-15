package hsf302.agricultural_products_project.service;

import hsf302.agricultural_products_project.model.Category;
import hsf302.agricultural_products_project.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryByLong(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }
}
