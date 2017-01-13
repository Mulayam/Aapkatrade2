package com.example.pat.aapkatrade.user_dashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.user_dashboard.companylist.CompanyData;
import com.example.pat.aapkatrade.user_dashboard.companylist.CompanyListAdapter;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity
{


    RecyclerView dashboardlist;
    DashboardAdapter dashboardAdapter;
    ArrayList<DashboardData> dashboardDatas = new ArrayList<>();
    TextView toolbar_title_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);

        setuptoolbar();

        setup_data();

        setup_layout();

        dashboardlist = (RecyclerView) findViewById(R.id.dashboardlist);

    }
    private void setuptoolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar_title_txt = (TextView) findViewById(R.id.title_txt);

        toolbar_title_txt.setText("Dashboard");

    }

    private void setup_layout()
    {
        dashboardlist = (RecyclerView) findViewById(R.id.dashboardlist);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),3);
        dashboardlist.setLayoutManager(layoutManager);

        dashboardAdapter = new DashboardAdapter(this, dashboardDatas);
        dashboardlist.setAdapter(dashboardAdapter);



    }


    private void setup_data()
    {
        dashboardDatas.clear();
        try
        {
            dashboardDatas.add(new DashboardData("","My Company",R.drawable.ic_companyprofile));
            dashboardDatas.add(new DashboardData("","My Profile",R.drawable.ic_myprofile));
            dashboardDatas.add(new DashboardData("","Change Password",R.drawable.ic_chngpswd));
            dashboardDatas.add(new DashboardData("","Add Company",R.drawable.ic_add_company));
            dashboardDatas.add(new DashboardData("","Company List",R.drawable.ic_lstcmpny));
            dashboardDatas.add(new DashboardData("","Add Product",R.drawable.ic_adprdct));
            dashboardDatas.add(new DashboardData("","List Product",R.drawable.ic_lstprdct));

        }catch (Exception  e){

        }
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
