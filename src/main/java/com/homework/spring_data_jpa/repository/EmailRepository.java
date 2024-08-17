package com.homework.spring_data_jpa.repository;

import com.homework.spring_data_jpa.model.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmailRepository extends JpaRepository<Email, Long> {

    boolean existsByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END " +
           "FROM Email e WHERE e.email = :email AND e.customer.customerId <> :customerId")
    boolean existsByEmailAndCustomerIdNot(String email, Long customerId);
}
