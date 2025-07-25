package hsf302.agricultural_products_project.service;

import hsf302.agricultural_products_project.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryByLong(Long id);
    void saveAll(List<Category> categories);
    Category findByName(String name);
}

