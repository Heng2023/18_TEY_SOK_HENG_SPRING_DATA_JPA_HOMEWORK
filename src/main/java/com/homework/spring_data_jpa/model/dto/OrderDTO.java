package com.homework.spring_data_jpa.model.dto;

import com.homework.spring_data_jpa.util.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class OrderDTO {

    private Long orderId;
    private LocalDate orderDate;
    private double totalAmount;

    @Enumerated(EnumType.STRING)
    private Status status;

    private List<ProductDTO> productList;
}
