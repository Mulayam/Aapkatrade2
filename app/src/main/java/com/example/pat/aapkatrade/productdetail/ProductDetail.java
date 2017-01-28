package com.example.pat.aapkatrade.productdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.user_dashboard.address.AddressActivity;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class ProductDetail extends AppCompatActivity
{

    MaterialSpinner spinner;
    private TextView buyProductButton;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_detail);


        setuptoolbar();
        initView();
        buyProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductDetail.this, AddressActivity.class));
            }
        });


    }

    private void initView()
    {
        String[] ITEMS = {"1", "2", "3", "4", "5", "6"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (MaterialSpinner) findViewById(R.id.spinner_qty);
        spinner.setAdapter(adapter);
        buyProductButton = (TextView) findViewById(R.id.buyProductButton);
    }

    private void setuptoolbar()
    {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(null);

       // getSupportActionBar().setIcon(R.drawable.home_logo);

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
