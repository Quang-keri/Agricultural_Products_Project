package hsf302.agricultural_products_project.repository;

import hsf302.agricultural_products_project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategory_CategoryId(int categoryId);
}

