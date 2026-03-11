package com.alice.springbootmall.service;

import com.alice.springbootmall.constant.ProductCategory;
import com.alice.springbootmall.dto.ProductQueryParams;
import com.alice.springbootmall.dto.ProductRequest;
import com.alice.springbootmall.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts(ProductQueryParams productQueryParams);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId,ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
