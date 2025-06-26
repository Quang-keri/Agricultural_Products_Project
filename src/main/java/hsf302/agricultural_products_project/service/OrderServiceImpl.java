package hsf302.agricultural_products_project.service;

import hsf302.agricultural_products_project.model.Order;

import hsf302.agricultural_products_project.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepo;

    @Override
    public Long createOrder(Long userId,double amount) {
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(amount);
        order.setPaymentMethod("pay card");
        order.setStatus("Pending");
        order.setCreatedAt(new Date());
        return orderRepo.save(order).getId();
    }

    @Override
    public Optional<Order> findLatestOrderByUser(Long userId) {
        return orderRepo.findTopByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public void updateOrderStatus(Long orderId, String status) {

        orderRepo.updateOrderStatus(orderId, status);
    }

    @Override
    public boolean existsById(Long orderId) {
        return orderRepo.existsById(orderId);
    }
}
