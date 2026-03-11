package com.alice.springbootmall.service;

import com.alice.springbootmall.dto.ProductRequest;
import com.alice.springbootmall.model.Product;

public interface ProductService {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId,ProductRequest productRequest);
}
