package hsf302.agricultural_products_project.repository;

import hsf302.agricultural_products_project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryId(int categoryId);
}
