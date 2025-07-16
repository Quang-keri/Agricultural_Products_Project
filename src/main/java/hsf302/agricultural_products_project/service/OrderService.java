package hsf302.agricultural_products_project.service;

import hsf302.agricultural_products_project.dto.CustomerOrderDto;
import hsf302.agricultural_products_project.model.Order;
import hsf302.agricultural_products_project.dto.OrderDTO;
import hsf302.agricultural_products_project.model.PaymentStatus;
import hsf302.agricultural_products_project.model.User;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order createOrder(User user, CustomerOrderDto order);

    Optional<Order> findLatestOrderByUser(Long userId);

    void updatePaymentStatus(Long orderId, PaymentStatus status);

    List<Order> findAll();

    boolean existsById(Long orderId);
}
