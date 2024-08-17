package com.homework.spring_data_jpa.model.dto;

import com.homework.spring_data_jpa.model.entity.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class CustomerDTO {

    private Long customerId;
    private String customerName;
    private String address;
    private String phoneNumber;
    private Email email;

    private List<OrderDTO> orderList;
}
