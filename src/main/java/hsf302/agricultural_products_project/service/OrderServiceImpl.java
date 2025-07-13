package hsf302.agricultural_products_project.service;

import hsf302.agricultural_products_project.dto.AgriculturalProductCartDto;
import hsf302.agricultural_products_project.dto.CustomerOrderDto;
import hsf302.agricultural_products_project.model.*;

import hsf302.agricultural_products_project.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private ProductService roductService;
    @Override
    public Long createOrder(User user, CustomerOrderDto order) {

        Order newOrder = orderRepo.save(Order.builder()
                .customerName(order.getName())
                .phoneNumber(order.getPhoneNumber())
                .address(order.getAddress())
                .totalPrice(BigDecimal.valueOf(order.getTotal()))
                .paymentStatus(order.getPaymentStatus())
                .orderStatus(OrderStatus.PENDING)
                .user(user)
                .build());
        //List<AgriculturalProduct> productList =
        for(AgriculturalProductCartDto item : order.getItems()) {
            OrderDetailId orderDetailId = new OrderDetailId();
            orderDetailId.setOrderId(newOrder.getOrderId());
            orderDetailId.setAgriculturalProductId(item.getAgriculturalProductId());
            OrderDetail orderDetail = OrderDetail.builder()
                    .orderDetailId(orderDetailId)
                    .order(newOrder)
                    .build();
        }


        return 1L;
    }

    @Override
    public Optional<Order> findLatestOrderByUser(Long userId) {
        //return orderRepo.findTopByUserIdOrderByCreatedAtDesc(userId);
        return null;
    }

    @Override
    public void updateOrderStatus(Long orderId, String status) {

        //orderRepo.updateOrderStatus(orderId, status);
    }

    @Override
    public boolean existsById(Long orderId) {
        return orderRepo.existsById(orderId);
    }
}
