package com.homework.spring_data_jpa.controller;

import com.homework.spring_data_jpa.model.dto.ApiResponse;
import com.homework.spring_data_jpa.model.dto.DeleteAndErrorResponse;
import com.homework.spring_data_jpa.model.dto.ProductDTO;
import com.homework.spring_data_jpa.model.requestbody.ProductRequest;
import com.homework.spring_data_jpa.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping()
    public ResponseEntity<ApiResponse<ProductDTO>> createProduct(@RequestBody ProductRequest productRequest) {
        ProductDTO createdProduct = productService.createProduct(productRequest);

        ApiResponse<ProductDTO> apiResponse = new ApiResponse<>(
                HttpStatus.CREATED,
                "A product has been created successfully.",
                createdProduct
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> findProductById(@PathVariable Long id) {
        ProductDTO productDTO = productService.findProductById(id);

        ApiResponse<ProductDTO> apiResponse = new ApiResponse<>(
                HttpStatus.OK,
                "Product has been found successfully.",
                productDTO
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequest productRequest) {

        ProductDTO updatedProduct = productService.updateProduct(productRequest, id);

        ApiResponse<ProductDTO> apiResponse = new ApiResponse<>(
                HttpStatus.OK,
                "Product has been updated successfully.",
                updatedProduct
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> findAllProducts(
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "productName") String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection
    ) {
        List<ProductDTO> products = productService.findAllProducts(pageNo-1, pageSize, sortBy, sortDirection);

        ApiResponse<List<ProductDTO>> apiResponse = new ApiResponse<>(
                HttpStatus.OK,
                "List of products have been found successfully.",
                products
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteAndErrorResponse> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);

        DeleteAndErrorResponse apiResponse = new DeleteAndErrorResponse(
                HttpStatus.OK,
                "Product has been deleted successfully."
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
