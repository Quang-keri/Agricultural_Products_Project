package hsf302.agricultural_products_project.repository;

import hsf302.agricultural_products_project.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
    List<User> findTop5ByOrderByUserIdDesc();
    Page<User> findByUserNameContainingIgnoreCase(String userName, Pageable pageable);


}
