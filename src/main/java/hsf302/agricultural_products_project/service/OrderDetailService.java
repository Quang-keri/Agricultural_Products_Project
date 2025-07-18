package hsf302.agricultural_products_project.service;

import hsf302.agricultural_products_project.dto.OrderDetailDTO;

import java.util.List;

public interface OrderDetailService {
     List<OrderDetailDTO> getOrderDetailDTOsByOrderId(int orderId);
}
