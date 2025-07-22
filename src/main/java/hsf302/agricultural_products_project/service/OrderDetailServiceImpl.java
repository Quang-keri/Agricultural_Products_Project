package hsf302.agricultural_products_project.service;

import hsf302.agricultural_products_project.dto.OrderDetailDTO;
import hsf302.agricultural_products_project.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetailDTO> getOrderDetailDTOsByOrderId(int orderId) {
        List<Object[]> results = orderDetailRepository.getOrderDetailsByOrderId(orderId);

        return results.stream().map(row -> new OrderDetailDTO(
                (String) row[0],
                BigDecimal.valueOf(((Number) row[2]).doubleValue()) ,
                ((Number) row[1]).intValue()
        )).collect(Collectors.toList());
    }
}
