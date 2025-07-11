package hsf302.agricultural_products_project.repository;

import hsf302.agricultural_products_project.model.AgriculturalProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgriculturalProdcutRepo extends JpaRepository<AgriculturalProduct, Long> {
}
