package com.alice.springbootmall.controller;

import com.alice.springbootmall.model.Product;
import com.alice.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
        Product product =productService.getProductById(productId);
        if(product != null){
            return ResponseEntity.status(HttpStatus.OK).body(product);//body(product):spring會自動把product轉成JSON
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();//build():建立ResponseEntity，但不帶body
        }
    }
}
