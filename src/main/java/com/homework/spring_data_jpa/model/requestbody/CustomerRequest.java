package com.homework.spring_data_jpa.model.requestbody;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerRequest {

    private String customerName;
    private String address;
    private String phoneNumber;
    private String email;
}

