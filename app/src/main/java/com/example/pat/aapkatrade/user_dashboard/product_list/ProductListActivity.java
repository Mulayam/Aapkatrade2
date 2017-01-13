package com.example.pat.aapkatrade.user_dashboard.product_list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
        product_list = (RecyclerView) findViewById(R.id.productlist);

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

        toolbar_title_txt = (TextView) findViewById(R.id.title_txt);

        toolbar_title_txt.setText("List Product");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {

            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }


}
