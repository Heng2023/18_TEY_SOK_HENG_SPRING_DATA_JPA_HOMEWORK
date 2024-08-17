package com.homework.spring_data_jpa.model.requestbody;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private int quantity;
    private int productId;
}
