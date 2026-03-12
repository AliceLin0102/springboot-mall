package com.alice.springbootmall.dao.impl;

import com.alice.springbootmall.constant.ProductCategory;
import com.alice.springbootmall.dao.ProductDao;
import com.alice.springbootmall.dto.ProductQueryParams;
import com.alice.springbootmall.dto.ProductRequest;
import com.alice.springbootmall.model.Product;
import com.alice.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductDaoImpl implements ProductDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams) {

        String sql="SELECT count(*) FROM product WHERE 1=1";

        Map<String,Object> map=new HashMap<>();

        if(productQueryParams.getCategory() != null){
            sql=sql +" AND category=:category ";//AND前面記得要留空白
            map.put("category",productQueryParams.getCategory().name());
        }
        if(productQueryParams.getSearch() != null) {
            sql = sql + " AND product_name LIKE :search ";
            map.put("search", "%" + productQueryParams.getSearch() + "%");
        }

        Integer total =  namedParameterJdbcTemplate.queryForObject(sql,map,Integer.class);//Integer.class：表示要將返回值設定為Integer

        return total;
    }

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {

        String sql="SELECT product_id,product_name,category,image_url,price,stock,description," +
                "created_date,last_modified_date FROM product WHERE 1=1" ;

        Map<String,Object> map=new HashMap<>();

        //查詢條件
        //WHERE 1=1:讓拼接可以使用
        if(productQueryParams.getCategory() != null){
            sql=sql +" AND category=:category ";//AND前面記得要留空白
            map.put("category",productQueryParams.getCategory().name());//.name()永遠固定，不會被修改。.toString()有可能被override
        }
        if(productQueryParams.getSearch() != null) {
            sql = sql + " AND product_name LIKE :search ";
            map.put("search", "%" + productQueryParams.getSearch() + "%");//模糊查詢一定要寫在map裡，不能寫在上方sql。
        }
        //排序
        //ORDER BY只能用拼接，不能用變數！
        //ORDER BY不用驗證是否為null，因為controller有給defaultValue，就不會是null
        //ORDER BY前後都要記得留下空白鍵
        sql=sql+" ORDER BY "+productQueryParams.getOrderBy()+" "+productQueryParams.getSort();

        //分頁
        sql=sql+" LIMIT :limit OFFSET :offset ";
        map.put("limit",productQueryParams.getLimit());//(最多)取得幾筆
        map.put("offset",productQueryParams.getOffset());//跳過幾筆後開始取

        List<Product> productList=namedParameterJdbcTemplate.query(sql,map,new ProductRowMapper());

        return productList;
    }

    @Override
    public Product getProductById(Integer productId) {//:productId這種方式叫做Named Parameter
        String sql="SELECT product_id,product_name,category,image_url,price,stock,description," +
                "created_date,last_modified_date FROM product WHERE product_id =:productId";

        //建立SQL參數
        Map<String,Object> map= new HashMap<>();
        map.put("productId",productId);

        //執行SQL：
        //query()方法一定回傳List<T>
        //因為是用Named Parameter所以需要用Map，讓Spring知道sql中的:productId是對應到哪個參數名字
        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        if( productList.size() > 0){
            return productList.get(0);//productId是唯一，因此只會回傳一筆，等同於第一筆。
        }else {
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql="INSERT INTO Product(product_name,category,image_url,price,stock,description,created_date,last_modified_date)VALUES(:productName,:category,:imageUrl,:price,:stock,:description,:createdDate,:lastModifiedDate)";

        Map<String,Object> map=new HashMap<>();
        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory().toString());
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());

        Date now=new Date();
        map.put("createdDate",now);
        map.put("lastModifiedDate",now);

        KeyHolder keyHolder=new GeneratedKeyHolder(); //KeyHolder(Spring提供的)用途:拿到資料庫自動生成的主鍵
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
        int productId = keyHolder.getKey().intValue();//.getKey()後回傳的是Number(可能是BigDecimal,Integer,Long,Double)，因此需要再.intValue();轉成int。
        return productId;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        String sql ="UPDATE product SET product_name=:productName,category=:category,image_Url=:imageUrl," +
                "price=:price,stock=:stock,description=:description,last_modified_date=:lastModifiedDate " +
                "WHERE product_id=:productId";

        Map<String,Object> map=new HashMap<>();
        map.put("productId",productId);

        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory().toString());
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());

        Date now =new Date();
        map.put("lastModifiedDate",now);

        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void deleteProductById(Integer productId) {
        String sql="DELETE FROM product WHERE product_id=:productId";

        Map<String,Object> map =new HashMap<>();
        map.put("productId",productId);

        namedParameterJdbcTemplate.update(sql,map);
    }
}
