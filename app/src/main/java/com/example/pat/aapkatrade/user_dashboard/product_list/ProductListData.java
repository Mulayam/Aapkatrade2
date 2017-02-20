package com.example.pat.aapkatrade.user_dashboard.product_list;

/**
 * Created by PPC16 on 11-Jan-17.
 */

public class ProductListData {

    String product_id, product_name, product_price, product_cross_price, product_image,category_name;

    public ProductListData(String product_id, String product_name, String product_price, String product_cross_price, String product_image,String category_name)
    {

        this.product_id = product_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_cross_price = product_cross_price;
        this.product_image = product_image;
        this.category_name = category_name;

    }
}
