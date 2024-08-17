package com.homework.spring_data_jpa.repository;

import com.homework.spring_data_jpa.model.entity.Customer;
import com.homework.spring_data_jpa.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomer(Customer customer);
}
