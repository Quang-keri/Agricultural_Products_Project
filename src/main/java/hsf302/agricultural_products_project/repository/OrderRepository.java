package hsf302.agricultural_products_project.repository;

import hsf302.agricultural_products_project.model.Order;
import hsf302.agricultural_products_project.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    //Optional<Order> findTopByUserIdOrderByCreatedAtDesc(Long userId);


    //    @Modifying
//    @Transactional
//   // @Query("UPDATE Order o SET o.status = :status WHERE o.id = :orderId")
//    void updateOrderStatus(@Param("orderId") Long orderId, @Param("status") String status);
    List<Order> findByUser_UserIdAndOrderStatusIn(Long userId, List<OrderStatus> statuses);
}

