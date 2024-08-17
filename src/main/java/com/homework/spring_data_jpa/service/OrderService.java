package com.homework.spring_data_jpa.service;

import com.homework.spring_data_jpa.model.dto.OrderDTO;
import com.homework.spring_data_jpa.model.requestbody.OrderRequest;
import com.homework.spring_data_jpa.util.Status;

import java.util.List;

public interface OrderService {

    OrderDTO createOrder(List<OrderRequest> orderRequest, Long customerId);

    OrderDTO findOrderById(Long orderId);

    List<OrderDTO> findOrdersByCustomerId(Long customerId);

    OrderDTO updateOrderStatus(Long orderId, Status status);

    List<OrderDTO> findAllOrders();
}
