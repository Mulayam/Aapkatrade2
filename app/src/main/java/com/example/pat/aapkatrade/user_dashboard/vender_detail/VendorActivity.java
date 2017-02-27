package com.example.pat.aapkatrade.user_dashboard.vender_detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.user_dashboard.order_list.OrderListAdapter;
import com.example.pat.aapkatrade.user_dashboard.order_list.OrderListData;

import java.util.ArrayList;

public class VendorActivity extends AppCompatActivity {


    private ArrayList<VendorData> venderListDatas = new ArrayList<>();
    private RecyclerView vender_list;
    private VendorAdapter vendorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);


        setuptoolbar();

        setup_data();

        setup_layout();

    }

    private void setup_data() {
        venderListDatas.clear();
        try {
            venderListDatas.add(new VendorData("", "", ""));
            venderListDatas.add(new VendorData("", "", ""));
            venderListDatas.add(new VendorData("", "", ""));
            venderListDatas.add(new VendorData("", "", ""));

        } catch (Exception e) {

        }
    }

    private void setup_layout() {
        vender_list = (RecyclerView) findViewById(R.id.vender_list);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        vendorAdapter = new VendorAdapter(this, venderListDatas, VendorActivity.this);

        vender_list.setAdapter(vendorAdapter);

        vender_list.setLayoutManager(mLayoutManager);
    }

    private void setuptoolbar() {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }


}
