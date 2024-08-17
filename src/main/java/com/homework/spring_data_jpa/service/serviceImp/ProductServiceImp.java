package com.homework.spring_data_jpa.service.serviceImp;

import com.homework.spring_data_jpa.component.ProductMapper;
import com.homework.spring_data_jpa.exception.ResourceNotFoundException;
import com.homework.spring_data_jpa.model.dto.ProductDTO;
import com.homework.spring_data_jpa.model.entity.Product;
import com.homework.spring_data_jpa.model.requestbody.ProductRequest;
import com.homework.spring_data_jpa.repository.ProductRepository;
import com.homework.spring_data_jpa.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ProductDTO createProduct(ProductRequest productRequest) {
        Product product = productMapper.toEntity(productRequest);

        Product savedProduct = productRepository.save(product);

        return productMapper.toDTO(savedProduct);
    }

    @Override
    public ProductDTO findProductById(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        return productMapper.toDTO(product);
    }

    @Override
    public ProductDTO updateProduct(ProductRequest productRequest, Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        existingProduct.setProductName(productRequest.getProductName());
        existingProduct.setUnitPrice(productRequest.getUnitPrice());
        existingProduct.setDescription(productRequest.getDescription());

        Product updatedProduct = productRepository.save(existingProduct);

        return productMapper.toDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
         Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(existingProduct);
    }

    @Override
    public List<ProductDTO> findAllProducts(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));

        Page<ProductDTO> productPage = productRepository.findAll(pageable)
                .map(productMapper::toDTO);
        return productPage.getContent();
    }
}
