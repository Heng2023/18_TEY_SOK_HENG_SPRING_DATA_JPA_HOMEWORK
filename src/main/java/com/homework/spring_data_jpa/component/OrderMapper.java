package com.homework.spring_data_jpa.component;

import com.homework.spring_data_jpa.model.dto.OrderDTO;
import com.homework.spring_data_jpa.model.dto.ProductDTO;
import com.homework.spring_data_jpa.model.entity.Customer;
import com.homework.spring_data_jpa.model.entity.Order;
import com.homework.spring_data_jpa.model.entity.Product;
import com.homework.spring_data_jpa.model.entity.ProductOrder;
import com.homework.spring_data_jpa.model.requestbody.OrderRequest;
import com.homework.spring_data_jpa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    @Autowired
    private ProductRepository productRepository;

    // Convert OrderRequest to Order entity
    public Order toEntity(List<OrderRequest> orderRequests, Customer customer) {
        if (orderRequests == null || customer == null) {
            return null;
        }

        Order order = new Order();
        order.setCustomer(customer);

        List<ProductOrder> productOrders = orderRequests.stream().map(orderRequest -> {
            Product product = productRepository.findById((long) orderRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            ProductOrder productOrder = new ProductOrder();
            productOrder.setProduct(product);
            productOrder.setQuantity(orderRequest.getQuantity());
            productOrder.setOrder(order);  // Link to the Order

            return productOrder;
        }).collect(Collectors.toList());

        order.setProductOrders(productOrders);

        return order;
    }

    // Convert Order entity to OrderDTO
    public OrderDTO toDTO(Order order) {
        if (order == null) {
            return null;
        }

        List<ProductDTO> productDTOList = order.getProductOrders().stream()
                .map(productOrder -> toProductDTO(productOrder.getProduct()))
                .collect(Collectors.toList());

        return new OrderDTO(
                order.getOrderId(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getStatus(),
                productDTOList
        );
    }

    // Convert Product entity to ProductDTO
    private ProductDTO toProductDTO(Product product) {
        if (product == null) {
            return null;
        }

        return new ProductDTO(
                product.getProductId(),
                product.getProductName(),
                product.getUnitPrice(),
                product.getDescription()
        );
    }
}
