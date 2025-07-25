package hsf302.agricultural_products_project.repository;

import hsf302.agricultural_products_project.model.CartItem;
import hsf302.agricultural_products_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {
    void deleteAllByCart_User(User user);
}
