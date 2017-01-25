package com.example.pat.aapkatrade.user_dashboard.address.viewpager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.pat.aapkatrade.R;

public class CartCheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_checkout);
        setuptoolbar();
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_cart_checkout, new CartCheckoutFragment()).addToBackStack(null).commit();
    }


    private void setuptoolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setIcon(R.drawable.home_logo);
    }
}
