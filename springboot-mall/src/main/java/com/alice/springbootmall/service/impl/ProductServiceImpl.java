package com.alice.springbootmall.service.impl;

import com.alice.springbootmall.dao.ProductDao;
import com.alice.springbootmall.dto.ProductRequest;
import com.alice.springbootmall.model.Product;
import com.alice.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }
}
