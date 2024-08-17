package com.homework.spring_data_jpa.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private HttpStatus status;
    private String message;
    private T payload;
}
