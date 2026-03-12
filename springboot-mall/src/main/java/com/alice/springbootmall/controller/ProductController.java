package com.alice.springbootmall.controller;

import com.alice.springbootmall.constant.ProductCategory;
import com.alice.springbootmall.dto.ProductQueryParams;
import com.alice.springbootmall.dto.ProductRequest;
import com.alice.springbootmall.model.Product;
import com.alice.springbootmall.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    //查詢商品列表
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(
            //查詢條件(filtering)
            @RequestParam(required = false ) ProductCategory category,
            @RequestParam(required = false) String search,

            //排序(sorting)
            @RequestParam(defaultValue = "created_date") String orderBy,//預設是用created_date"來商品排序，若有設定值就會以值為主
            @RequestParam(defaultValue = "desc") String sort, //預設用降序

            //分頁(Pagination)
            @RequestParam(defaultValue = "5")  @Max(1000) @Min(0) Integer limit,//一次不能超過1000筆，最小為0，不能為負數
            @RequestParam(defaultValue = "0")  @Min(0) Integer offset
    ){
        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        //products是一種資源，就算是空的，都會回傳200，不會回傳404，這是RESTFul的設計原則
        List<Product> productList=productService.getProducts(productQueryParams);
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
