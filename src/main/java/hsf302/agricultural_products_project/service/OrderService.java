package hsf302.agricultural_products_project.service;

import hsf302.agricultural_products_project.model.Order;
import hsf302.agricultural_products_project.dto.OrderDTO;

import java.util.Optional;

public interface OrderService {
    Long createOrder(Long userId, double amount);
    Optional<Order> findLatestOrderByUser(Long userId);
    void updateOrderStatus(Long orderId, String status);


    boolean existsById(Long orderId);
}
