package com.homework.spring_data_jpa.service.serviceImp;

import com.homework.spring_data_jpa.component.OrderMapper;
import com.homework.spring_data_jpa.exception.ResourceNotFoundException;
import com.homework.spring_data_jpa.model.dto.OrderDTO;
import com.homework.spring_data_jpa.model.entity.Customer;
import com.homework.spring_data_jpa.model.entity.Order;
import com.homework.spring_data_jpa.model.requestbody.OrderRequest;
import com.homework.spring_data_jpa.repository.CustomerRepository;
import com.homework.spring_data_jpa.repository.OrderRepository;
import com.homework.spring_data_jpa.service.OrderService;
import com.homework.spring_data_jpa.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository; // Assuming you have a repository for Customer

    @Override
    public OrderDTO createOrder(List<OrderRequest> orderRequests, Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Order order = orderMapper.toEntity(orderRequests, customer);

        order.setOrderDate(LocalDate.now());
        order.setStatus(Status.PENDING);

        double totalAmount = order.getProductOrders().stream()
                .mapToDouble(productOrder -> productOrder.getProduct().getUnitPrice() * productOrder.getQuantity())
                .sum();
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        return orderMapper.toDTO(savedOrder);
    }

    @Override
    public OrderDTO findOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        return orderMapper.toDTO(order);
    }

    @Override
    public List<OrderDTO> findOrdersByCustomerId(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));

        List<Order> orders = orderRepository.findByCustomer(customer);

        return orders.stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO updateOrderStatus(Long orderId, Status status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);

        return orderMapper.toDTO(updatedOrder);
    }

    @Override
    public List<OrderDTO> findAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }
}