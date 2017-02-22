package com.example.pat.aapkatrade.user_dashboard.order_list;

/**
 * Created by PPC16 on 17-Jan-17.
 */

public class OrderListData
{
    String order_id, product_name, product_creation_date;

    OrderListData(String order_id, String product_name, String product_creation_date)
    {
        this.order_id = order_id;
        this.product_name = product_name;
        this.product_creation_date = product_creation_date;

    }
}


