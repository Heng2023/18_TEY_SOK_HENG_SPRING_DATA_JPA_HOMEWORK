package com.homework.spring_data_jpa.service;

import com.homework.spring_data_jpa.model.dto.ProductDTO;
import com.homework.spring_data_jpa.model.requestbody.ProductRequest;

import java.util.List;

public interface ProductService {

    ProductDTO createProduct(ProductRequest productRequest);

    ProductDTO findProductById(Long id);

    ProductDTO updateProduct(ProductRequest productRequest, Long id);

    void deleteProduct(Long id);

    List<ProductDTO> findAllProducts(int pageNo, int pageSize, String sortBy, String sortDirection);
}
