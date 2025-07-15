package hsf302.agricultural_products_project.service;

import hsf302.agricultural_products_project.dto.AgriculturalProductCartDto;
import hsf302.agricultural_products_project.dto.CustomerOrderDto;
import hsf302.agricultural_products_project.model.*;

import hsf302.agricultural_products_project.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private ProductService productService;
    @Override
    @Transactional
    public Order createOrder(User user, CustomerOrderDto order) {

        Order newOrder = orderRepo.save(Order.builder()
                .customerName(order.getName())
                .phoneNumber(order.getPhoneNumber())
                .address(order.getAddress())
                .totalPrice(BigDecimal.valueOf(order.getTotal()+30000))
                .paymentStatus(order.getPaymentStatus())
                .paymentMethod(order.getPaymentMethod())
                .orderStatus(OrderStatus.PENDING)
                .user(user)
                .build());
        List<Long> productIds = order.getItems().stream()
                .map(AgriculturalProductCartDto::getAgriculturalProductId)
                .toList();

        Map<Long, Integer> productQuantities = order.getItems().stream()
                .collect(Collectors.toMap(AgriculturalProductCartDto::getAgriculturalProductId, AgriculturalProductCartDto::getQuantity));

        List<AgriculturalProduct> productList = productService.getAllProductsById(productIds);
        List<OrderDetail> orderDetails = new ArrayList<>();
        for( AgriculturalProduct item :productList){
            OrderDetailId orderDetailId = new OrderDetailId();
            orderDetailId.setOrderId(newOrder.getOrderId());
            orderDetailId.setAgriculturalProductId(item.getAgriculturalProductId());
            Integer quantity = productQuantities.get(item.getAgriculturalProductId());
            OrderDetail orderDetail = OrderDetail.builder()
                    .orderDetailId(orderDetailId)
                    .order(newOrder)
                    .agriculturalProduct(item)
                    .quantity(quantity)
                    .build();
            orderDetails.add(orderDetail);
        }
        newOrder.setOrderDetails(orderDetails);

        return orderRepo.save(newOrder);
    }

    @Override
    public Optional<Order> findLatestOrderByUser(Long userId) {
        //return orderRepo.findTopByUserIdOrderByCreatedAtDesc(userId);
        return null;
    }

    @Override
    public void updatePaymentStatus(Long orderId, PaymentStatus status) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("Order not found with id: " + orderId));
        order.setPaymentStatus(status);
        orderRepo.save(order);
    }

    @Override
    public Order findOrderById(Long orderId) {
        return orderRepo.findById(orderId).orElse(null);
    }

    @Override
    public boolean existsById(Long orderId) {
        return orderRepo.existsById(orderId);
    }
}
