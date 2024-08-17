package com.homework.spring_data_jpa.component;

import com.homework.spring_data_jpa.model.dto.ProductDTO;
import com.homework.spring_data_jpa.model.entity.Product;
import com.homework.spring_data_jpa.model.requestbody.ProductRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    // Convert ProductRequest to Product entity
    public Product toEntity(ProductRequest productRequest) {
        if (productRequest == null) {
            return null;
        }

        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        product.setDescription(productRequest.getDescription());
        product.setUnitPrice(productRequest.getUnitPrice());

        return product;
    }

    // Convert Product entity to ProductDTO
    public ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }

        return new ProductDTO(
                product.getProductId(),
                product.getProductName(),
                product.getUnitPrice(),
                product.getDescription()
        );
    }
}
