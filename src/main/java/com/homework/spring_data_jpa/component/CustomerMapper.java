package com.homework.spring_data_jpa.component;

import com.homework.spring_data_jpa.model.dto.CustomerDTO;
import com.homework.spring_data_jpa.model.dto.OrderDTO;
import com.homework.spring_data_jpa.model.entity.Customer;
import com.homework.spring_data_jpa.model.entity.Email;
import com.homework.spring_data_jpa.model.requestbody.CustomerRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {

    private final OrderMapper orderMapper;

    public CustomerMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    // Convert CustomerRequest to Customer entity
    public Customer toEntity(CustomerRequest customerRequest) {
        if (customerRequest == null) {
            return null;
        }

        Customer customer = new Customer();
        customer.setCustomerName(customerRequest.getCustomerName());
        customer.setAddress(customerRequest.getAddress());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());

        Email email = new Email();
        email.setEmail(customerRequest.getEmail());
        customer.setEmail(email);

        return customer;
    }

    // Convert Customer entity to CustomerDTO
    public CustomerDTO toDTO(Customer customer) {
        if (customer == null) {
            return null;
        }

        List<OrderDTO> orderDTOList = customer.getOrders().stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());

        return new CustomerDTO(
                customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getAddress(),
                customer.getPhoneNumber(),
                customer.getEmail(),
                orderDTOList
        );
    }

    public void updateEntityFromRequest(Customer existingCustomer, CustomerRequest customerRequest) {
        if (existingCustomer == null || customerRequest == null) {
            return;
        }

        existingCustomer.setCustomerName(customerRequest.getCustomerName());
        existingCustomer.setAddress(customerRequest.getAddress());
        existingCustomer.setPhoneNumber(customerRequest.getPhoneNumber());

        // Update the existing email entity
        Email existingEmail = existingCustomer.getEmail();
        if (existingEmail != null && !existingEmail.getEmail().equals(customerRequest.getEmail())) {
            existingEmail.setEmail(customerRequest.getEmail());
        }
    }
}
