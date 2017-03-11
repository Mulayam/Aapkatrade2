package com.example.pat.aapkatrade.user_dashboard.service_enquirylist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.App_sharedpreference;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import java.util.ArrayList;


public class ServiceEnquiryActivity extends AppCompatActivity
{

    RecyclerView recyclerViewcompanylist;
    ServiceEnquiryAdapter serviceEnquiryAdapter;
    ArrayList<ServiceEnquiryData> serviceEnquiryDatas = new ArrayList<>();
    RelativeLayout relativeCompanylist;
    ProgressBarHandler progress_handler;
    App_sharedpreference app_sharedpreference;
    String user_id;
    private final static String TAG_FRAGMENT = "TAG_FRAGMENT";



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_enquiry);

        setuptoolbar();

        setup_layout();
    }


    private void setuptoolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
        }
    }


    private void setup_layout()
    {
        relativeCompanylist = (RelativeLayout) findViewById(R.id.relativeCompanylist);

        recyclerViewcompanylist = (RecyclerView) findViewById(R.id.companylist);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerViewcompanylist.setLayoutManager(mLayoutManager);

        get_company_list_data();

        serviceEnquiryAdapter = new ServiceEnquiryAdapter(ServiceEnquiryActivity.this, serviceEnquiryDatas, ServiceEnquiryActivity.this);

        recyclerViewcompanylist.setAdapter(serviceEnquiryAdapter);


        app_sharedpreference = new App_sharedpreference(ServiceEnquiryActivity.this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_map, menu);
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


    public void get_company_list_data()
    {
        relativeCompanylist.setVisibility(View.INVISIBLE);
        progress_handler.show();
        serviceEnquiryDatas.clear();
        Ion.with(ServiceEnquiryActivity.this)
                .load("http://aapkatrade.com/slim/enquiry_service_list")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "company")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("user_id", app_sharedpreference.getsharedpref("userid", "0"))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result)
                    {
                        if (result == null)
                        {

                            progress_handler.hide();
                        }
                        else
                        {
                            Log.e("data===============", result.toString());

                            JsonObject jsonObject = result.getAsJsonObject();

                            JsonArray jsonArray = jsonObject.getAsJsonArray("result");

                            for (int i = 0; i < jsonArray.size(); i++)
                            {
                                JsonObject jsonObject2 = (JsonObject) jsonArray.get(i);

                                String service_enquiry_id = jsonObject2.get("id").getAsString();

                                String product_name = jsonObject2.get("product_name").getAsString();

                                String product_price = jsonObject2.get("price").getAsString();

                                String user_name = jsonObject2.get("name").getAsString();

                                String user_email= jsonObject2.get("email").getAsString();

                                String user_mobile = jsonObject2.get("mobile").getAsString();

                                String description = jsonObject2.get("short_des").getAsString();

                                String created_date = jsonObject2.get("created_at").getAsString();

                                serviceEnquiryDatas.add(new ServiceEnquiryData(service_enquiry_id, product_name, product_price,user_name,user_email,user_mobile,description,created_date));

                            }

                            serviceEnquiryAdapter.notifyDataSetChanged();

                            progress_handler.hide();
                            // progressView.setVisibility(View.INVISIBLE);
                            relativeCompanylist.setVisibility(View.VISIBLE);


                        }

                    }

                });
    }


}
