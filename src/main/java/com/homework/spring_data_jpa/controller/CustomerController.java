package com.homework.spring_data_jpa.controller;

import com.homework.spring_data_jpa.model.dto.ApiResponse;
import com.homework.spring_data_jpa.model.dto.CustomerDTO;
import com.homework.spring_data_jpa.model.dto.DeleteAndErrorResponse;
import com.homework.spring_data_jpa.model.entity.Customer;
import com.homework.spring_data_jpa.model.requestbody.CustomerRequest;
import com.homework.spring_data_jpa.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping()
    public ResponseEntity<ApiResponse<Customer>> createCustomer(@RequestBody CustomerRequest customerRequest) {
        Customer customer = customerService.createCustomer(customerRequest);

        ApiResponse<Customer> apiResponse = new ApiResponse<>(
                HttpStatus.CREATED,
                "A customer has been created successfully.",
                customer
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<CustomerDTO>>> findAllCustomers(
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "customerName") String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection
    ) {
        List<CustomerDTO> customers = customerService.findAllCustomers(pageNo-1, pageSize, sortBy, sortDirection);

        ApiResponse<List<CustomerDTO>> apiResponse = new ApiResponse<>(
                HttpStatus.OK,
                "List of customers have been found successfully.",
                customers
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerDTO>> findCustomerById(@PathVariable Long id) {
        CustomerDTO customerDTO = customerService.findCustomerById(id);

        ApiResponse<CustomerDTO> apiResponse = new ApiResponse<>(
                HttpStatus.OK,
                "Customer has been found successfully.",
                customerDTO
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerDTO>> updateCustomer(
            @PathVariable Long id,
            @RequestBody CustomerRequest customerRequest) {

        CustomerDTO updatedCustomer = customerService.updateCustomer(id, customerRequest);

        ApiResponse<CustomerDTO> apiResponse = new ApiResponse<>(
                HttpStatus.OK,
                "Customer has been updated successfully.",
                updatedCustomer
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteAndErrorResponse> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);

        DeleteAndErrorResponse apiResponse = new DeleteAndErrorResponse(
                HttpStatus.OK,
                "Customer has been deleted successfully."
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
