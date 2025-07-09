package hsf302.agricultural_products_project.repository;

import hsf302.agricultural_products_project.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findTopByUser_UserIdOrderByCreateAtDesc(Long userUserId);

    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.statusOrder = :status WHERE o.orderID = :orderId")
    void updateOrderStatus(@Param("orderId") Long orderId, @Param("status") String status);
}

