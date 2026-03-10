package com.alice.springbootmall.dao;

import com.alice.springbootmall.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);

}
