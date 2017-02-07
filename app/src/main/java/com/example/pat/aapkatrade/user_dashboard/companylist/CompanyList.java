package com.example.pat.aapkatrade.user_dashboard.companylist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;


public class CompanyList extends AppCompatActivity
{

    CircularProgressView progressView;
    RecyclerView recyclerViewcompanylist;
    CompanyListAdapter companyListAdapter;
    ArrayList<CompanyData> companyDatas = new ArrayList<>();
    RelativeLayout relativeCompanylist;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);

        setuptoolbar();

        setup_layout();

    }

    public void get_company_list_data()
    {
        relativeCompanylist.setVisibility(View.INVISIBLE);
        progressView.startAnimation();
        companyDatas.clear();
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
                        if(result ==null)
                        {

                            progressView.stopAnimation();

                        }
                        else
                        {
                            Log.e("data===============",result.toString());

                            JsonObject jsonObject = result.getAsJsonObject();

                            JsonArray jsonArray = jsonObject.getAsJsonArray("result");

                            System.out.println("jsonArray11111111111111111"+jsonArray.toString());

                            for(int i = 0; i<jsonArray.size(); i++)
                            {

                                JsonObject jsonObject2 = (JsonObject) jsonArray.get(i);

                                System.out.println("jsonArray jsonObject2"+jsonObject2.toString());

                                String country_id = jsonObject2.get("companyId").getAsString();

                                String name = jsonObject2.get("name").getAsString();

                                String creation_date = jsonObject2.get("created").getAsString();

                                System.out.println("ferhgjerk"+country_id+name+creation_date);

                                companyDatas.add(new CompanyData(country_id,name,creation_date));

                            }

                            companyListAdapter = new CompanyListAdapter(CompanyList.this, companyDatas , CompanyList.this);

                            recyclerViewcompanylist.setAdapter(companyListAdapter);

                            companyListAdapter.notifyDataSetChanged();

                            progressView.stopAnimation();
                           // progressView.setVisibility(View.INVISIBLE);
                            relativeCompanylist.setVisibility(View.VISIBLE);

                        }

                    }

                });

    }


    private void setup_layout()
    {

        progressView = (CircularProgressView) findViewById(R.id.progress_view);

        relativeCompanylist = (RelativeLayout) findViewById(R.id.relativeCompanylist);

        recyclerViewcompanylist = (RecyclerView) findViewById(R.id.companylist);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerViewcompanylist.setLayoutManager(mLayoutManager);

        get_company_list_data();
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
