package hsf302.agricultural_products_project.repository;

import hsf302.agricultural_products_project.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    @Query(value = """
        SELECT 
            ap.name AS productName,
            od.quantity AS quantity,
            (od.quantity * ap.price) AS totalPrice
        FROM order_details od
        JOIN agricultural_products ap 
            ON od.agricultural_product_id = ap.agricultural_product_id
        WHERE od.order_id = :orderId
        """, nativeQuery = true)
    List<Object[]> getOrderDetailsByOrderId(@Param("orderId") int orderId);
}
