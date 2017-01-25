package com.example.pat.aapkatrade.user_dashboard.companylist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;


public class CompanyList extends AppCompatActivity
{


    RecyclerView companylist;
    CompanyListAdapter companyListAdapter;
    ArrayList<CompanyData> companyDatas = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);

        setuptoolbar();

        get_web_data();
        setup_data();

        setup_layout();

    }

    private void get_web_data()
    {
        Ion.with(CompanyList.this)
                .load("http://aapkatrade.com/slim/listCompany")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "company")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("user_id", "1")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>()
                {
                    @Override
                    public void onCompleted(Exception e, JsonObject result)
                    {

                        Log.e("data===============",result.toString());

                        JsonObject jsonObject = result.getAsJsonObject();

                        JsonArray jsonArray = jsonObject.getAsJsonArray("");

                        for(int i = 0; i>jsonArray.size(); i++)
                        {

                            JsonObject js = jsonArray.get(i).getAsJsonObject();

                          //  companyDatas.add(js.get("dfgd").getAsString(),js.get("").getAsString(),js.get("").getAsString());
                        }


                    }

                });

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

        getSupportActionBar().setTitle(null);

        getSupportActionBar().setIcon(R.drawable.home_logo);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
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
