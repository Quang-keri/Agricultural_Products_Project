package hsf302.agricultural_products_project.repository;

import hsf302.agricultural_products_project.model.Cart;
import hsf302.agricultural_products_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<Cart,Long> {
    Optional<Cart> findByUser(User user);
}
