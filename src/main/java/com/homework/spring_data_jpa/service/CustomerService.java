package com.homework.spring_data_jpa.service;

import com.homework.spring_data_jpa.model.dto.CustomerDTO;
import com.homework.spring_data_jpa.model.entity.Customer;
import com.homework.spring_data_jpa.model.requestbody.CustomerRequest;

import java.util.List;

public interface CustomerService {

    Customer createCustomer(CustomerRequest customerRequest);

    List<CustomerDTO> findAllCustomers(int pageNo, int pageSize, String sortBy, String sortDirection);

    CustomerDTO findCustomerById(Long id);

    CustomerDTO updateCustomer(Long id, CustomerRequest customerRequest);

    void deleteCustomer(Long id);
}
