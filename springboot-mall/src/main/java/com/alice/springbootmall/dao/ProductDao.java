package com.alice.springbootmall.dao;

import com.alice.springbootmall.dto.ProductRequest;
import com.alice.springbootmall.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId,ProductRequest productRequest);

}
