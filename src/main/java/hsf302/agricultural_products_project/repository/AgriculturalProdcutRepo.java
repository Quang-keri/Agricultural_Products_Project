package hsf302.agricultural_products_project.repository;

import hsf302.agricultural_products_project.model.AgriculturalProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgriculturalProdcutRepo extends JpaRepository<AgriculturalProduct, Long> {
    List<AgriculturalProduct> findAllByCategory_CategoryId(int categoryId);

    List<AgriculturalProduct> findByNameContainingIgnoreCase(String name);
    Page<AgriculturalProduct> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
