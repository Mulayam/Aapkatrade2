package com.example.pat.aapkatrade.user_dashboard.product_list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity
{


    ArrayList<ProductListData> productListDatas = new ArrayList<>();
    RecyclerView product_list;
    ProductListAdapter productListAdapter;
   TextView toolbar_title_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        setuptoolbar();

        setup_data();

        setup_layout();

    }

    private void setup_data()
    {
        productListDatas.clear();
        try
        {
            productListDatas.add(new ProductListData("","",""));
            productListDatas.add(new ProductListData("","",""));
            productListDatas.add(new ProductListData("","",""));
            productListDatas.add(new ProductListData("","",""));

        }catch (Exception  e){

        }
    }

    private void setup_layout()
    {
        product_list = (RecyclerView) findViewById(R.id.product_list_recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        productListAdapter = new ProductListAdapter(this, productListDatas);
        product_list.setAdapter(productListAdapter);
        product_list.setLayoutManager(mLayoutManager);

    }

    private void setuptoolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Company List");




    }

}
