package com.alice.springbootmall.controller;

import com.alice.springbootmall.constant.ProductCategory;
import com.alice.springbootmall.dto.ProductRequest;
import com.alice.springbootmall.model.Product;
import com.alice.springbootmall.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    //查詢商品列表
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(
            @RequestParam(required = false ) ProductCategory category,
            @RequestParam(required = false) String search
    ){

        //products是一種資源，就算是空的，都會回傳200，不會回傳404，這是RESTFul的設計原則
        List<Product> productList=productService.getProducts(category,search);
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    //查詢某一個商品
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){ //@PathVariable：從URL路徑中取得變數值
        Product product =productService.getProductById(productId);
        if(product != null){
            return ResponseEntity.status(HttpStatus.OK).body(product);//body(product):spring會自動把product轉成JSON
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();//build():建立ResponseEntity，但不帶body
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
        Integer productId=productService.createProduct(productRequest);
        Product product=productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);//CREATED:狀態碼201，是當成功新增資料時，是HTTP標準建議回傳
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest){

        //檢查product是否存在
        Product  product=productService.getProductById(productId);
        if(product == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        //修改商品數據
        productService.updateProduct(productId,productRequest);

        Product updateProduct =productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId){

        productService.deleteProductById(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        //若沒有該Id，就算刪除，一樣會回傳204 No_Content
    }


}
