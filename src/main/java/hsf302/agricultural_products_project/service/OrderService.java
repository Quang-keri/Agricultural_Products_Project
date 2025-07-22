package hsf302.agricultural_products_project.service;

import hsf302.agricultural_products_project.dto.CustomerOrderDto;
import hsf302.agricultural_products_project.dto.OrderProcessDTO;
import hsf302.agricultural_products_project.model.Order;
import hsf302.agricultural_products_project.model.PaymentStatus;
import hsf302.agricultural_products_project.model.User;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order createOrder(User user, CustomerOrderDto order);
    Optional<Order> findLatestOrderByUser(Long userId);
    void updatePaymentStatus(Long orderId, PaymentStatus status);

   Order findOrderById(Long orderId);
    boolean existsById(Long orderId);
    List<OrderProcessDTO> getOrderManagement(Long userId);
    List<OrderProcessDTO> getOrderHistory(Long userId);
    List<Order> getAllOrders();
    long countOrders();
}
