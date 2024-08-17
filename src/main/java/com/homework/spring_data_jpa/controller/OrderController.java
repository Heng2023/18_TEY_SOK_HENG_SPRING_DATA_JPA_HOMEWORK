package com.homework.spring_data_jpa.controller;

import com.homework.spring_data_jpa.model.dto.ApiResponse;
import com.homework.spring_data_jpa.model.dto.OrderDTO;
import com.homework.spring_data_jpa.model.requestbody.OrderRequest;
import com.homework.spring_data_jpa.service.OrderService;
import com.homework.spring_data_jpa.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/{customerId}")
    public ResponseEntity<ApiResponse<OrderDTO>> createOrder(
            @RequestBody List<OrderRequest> orderRequests,
            @PathVariable Long customerId) {

        OrderDTO order = orderService.createOrder(orderRequests, customerId);

        ApiResponse<OrderDTO> apiResponse = new ApiResponse<>(
                HttpStatus.CREATED,
                "An order has been created successfully.",
                order
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDTO>> getOrderById(@PathVariable Long id) {
        OrderDTO orderDTO = orderService.findOrderById(id);

        ApiResponse<OrderDTO> apiResponse = new ApiResponse<>(
                HttpStatus.OK,
                "Order has been found successfully.",
                orderDTO
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getOrdersByCustomerId(@PathVariable Long customerId) {
        List<OrderDTO> orders = orderService.findOrdersByCustomerId(customerId);

        ApiResponse<List<OrderDTO>> apiResponse = new ApiResponse<>(
                HttpStatus.OK,
                "Orders for the customer have been found successfully.",
                orders
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/status")
    public ResponseEntity<ApiResponse<OrderDTO>> updateOrderStatus(
            @RequestParam Long orderId,
            @RequestParam Status status) {

        OrderDTO updatedOrder = orderService.updateOrderStatus(orderId, status);

        ApiResponse<OrderDTO> apiResponse = new ApiResponse<>(
                HttpStatus.OK,
                "Order status has been updated successfully.",
                updatedOrder
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getAllOrders() {
        List<OrderDTO> orders = orderService.findAllOrders();

        ApiResponse<List<OrderDTO>> apiResponse = new ApiResponse<>(
                HttpStatus.OK,
                "All orders have been retrieved successfully.",
                orders
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
