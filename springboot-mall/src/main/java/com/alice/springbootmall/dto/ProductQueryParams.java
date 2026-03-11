package com.alice.springbootmall.dto;

import com.alice.springbootmall.constant.ProductCategory;
//方便於用在多參數要傳時，後續好維護
public class ProductQueryParams {
    private ProductCategory category;
    private String search;

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
