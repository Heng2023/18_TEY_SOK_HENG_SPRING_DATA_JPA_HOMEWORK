package com.homework.spring_data_jpa.service.serviceImp;

import com.homework.spring_data_jpa.component.CustomerMapper;
import com.homework.spring_data_jpa.exception.EmailAlreadyExistsException;
import com.homework.spring_data_jpa.exception.ResourceNotFoundException;
import com.homework.spring_data_jpa.model.dto.CustomerDTO;
import com.homework.spring_data_jpa.model.entity.Customer;
import com.homework.spring_data_jpa.model.requestbody.CustomerRequest;
import com.homework.spring_data_jpa.repository.CustomerRepository;
import com.homework.spring_data_jpa.repository.EmailRepository;
import com.homework.spring_data_jpa.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImp implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private EmailRepository emailRepository;

    @Override
    public Customer createCustomer(CustomerRequest customerRequest) {

        if (emailRepository.existsByEmail(customerRequest.getEmail())) {
            throw new EmailAlreadyExistsException("Email is already in use: " + customerRequest.getEmail());
        }

        Customer customer = customerMapper.toEntity(customerRequest);
        Customer savedCustomer = customerRepository.save(customer);
        return customerRepository.save(savedCustomer);
    }

    @Override
    public List<CustomerDTO> findAllCustomers(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));

        Page<CustomerDTO> customerPage = customerRepository.findAll(pageable)
                .map(customerMapper::toDTO);
        return customerPage.getContent();
    }

    @Override
    public CustomerDTO findCustomerById(Long id) {

        Customer customer = customerRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Customer not found with id: " + id));
        return customerMapper.toDTO(customer);
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerRequest customerRequest) {
         Customer existingCustomer = customerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

    if (emailRepository.existsByEmailAndCustomerIdNot(customerRequest.getEmail(), id)) {
        throw new EmailAlreadyExistsException("Email is already in use: " + customerRequest.getEmail());
    }

    customerMapper.updateEntityFromRequest(existingCustomer, customerRequest);

    Customer updatedCustomer = customerRepository.save(existingCustomer);

    return customerMapper.toDTO(updatedCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer existingCustomer = customerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

        customerRepository.delete(existingCustomer);
    }
}
