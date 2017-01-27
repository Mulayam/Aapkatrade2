package com.example.pat.aapkatrade.user_dashboard.address;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.pat.aapkatrade.R;

import java.util.ArrayList;

public class AddressActivity extends AppCompatActivity {

    ArrayList<AddressData> addressList = new ArrayList<>();
    RecyclerView addressRecyclerView;
    AddressListAdapter addressListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        setuptoolbar();
        setup_data();
        setup_layout();
        }


    private void setuptoolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setIcon(R.drawable.home_logo);
    }


    private void setup_data() {
        addressList.clear();
        try {
            addressList.add(new AddressData("Line 1", "Line 2", "Line 3"));
            addressList.add(new AddressData("Line 1", "Line 2", "Line 3"));
            addressList.add(new AddressData("Line 1", "Line 2", "Line 3"));
            addressList.add(new AddressData("Line 1", "Line 2", "Line 3"));
            addressList.add(new AddressData("Line 1", "Line 2", "Line 3"));
            addressList.add(new AddressData("Line 1", "Line 2", "Line 3"));
        } catch (Exception e) {

        }
    }

    private void setup_layout() {
        addressRecyclerView = (RecyclerView) findViewById(R.id.addressRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        addressListAdapter = new AddressListAdapter(this, addressList);
        addressRecyclerView.setAdapter(addressListAdapter);
        addressRecyclerView.setLayoutManager(mLayoutManager);
    }

}
