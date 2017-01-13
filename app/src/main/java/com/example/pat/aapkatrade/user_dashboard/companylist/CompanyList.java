package com.example.pat.aapkatrade.user_dashboard.companylist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;

import java.util.ArrayList;


public class CompanyList extends AppCompatActivity
{

    RecyclerView companylist;
    TextView toolbar_title_txt;
    CompanyListAdapter companyListAdapter;
    ArrayList<CompanyData> companyDatas = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);

        setuptoolbar();

        setup_data();

        setup_layout();

    }

    private void setup_data()
    {
        companyDatas.clear();
        try
        {
            companyDatas.add(new CompanyData("","",""));
            companyDatas.add(new CompanyData("","",""));
            companyDatas.add(new CompanyData("","",""));
            companyDatas.add(new CompanyData("","",""));

        }catch (Exception  e){

        }
    }

    private void setup_layout()
    {
        companylist = (RecyclerView) findViewById(R.id.companylist);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        companyListAdapter = new CompanyListAdapter(this, companyDatas);
        companylist.setAdapter(companyListAdapter);
        companylist.setLayoutManager(mLayoutManager);

    }

    private void setuptoolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Company List");




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
