package com.homework.spring_data_jpa.model.requestbody;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    private String productName;
    private String description;
    private double unitPrice;
}
