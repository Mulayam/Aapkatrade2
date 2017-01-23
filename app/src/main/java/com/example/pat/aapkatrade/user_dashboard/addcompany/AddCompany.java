package com.example.pat.aapkatrade.user_dashboard.addcompany;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.ConnetivityCheck;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


public class AddCompany extends AppCompatActivity {

    String[] categoriesNames = {"India", "China", "Australia", "Portugle", "America", "New Zealand"};
    Spinner category, subcategory;
    TextView toolbar_title_txt;
    Button btnSave;
    private EditText companyName, address;
    private ProgressDialog dialog;
    private LinearLayout linearLayout;
    private Snackbar snackbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);
        setuptoolbar();
        initView();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCompany();
            }
        });
    }

    private void addCompany() {
        if(ConnetivityCheck.isNetworkAvailable(AddCompany.this)){
            dialog=new ProgressDialog(AddCompany.this);
            dialog.setMessage("Loading...\nPlease Wait");
            dialog.setCancelable(false);
            dialog.setInverseBackgroundForced(false);
            dialog.show();
            callAddCompanyWebService("1", companyName.getText().toString(), address.getText().toString());
            dialog.hide();
        }else {
            linearLayout.setVisibility(View.VISIBLE);
            snackbar = Snackbar.make(linearLayout, "Please Check your Network Connection", Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(getResources().getColor(android.R.color.holo_red_dark));
            snackbar.setAction("Retry", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addCompany();
                }
            });
            snackbar.show();
        }
    }

    private void callAddCompanyWebService(String userId, String companyName, String address) {
        Ion.with(AddCompany.this)
                .load("http://aapkatrade.com/slim/addCompany")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("user_id", userId)
                .setBodyParameter("company_name", companyName)
                .setBodyParameter("address", address)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        JsonObject jsonObject = result.getAsJsonObject();
                        String message = jsonObject.get("message").getAsString();
                        snackbar.make(linearLayout, message, Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    private void initView() {
        category = (Spinner) findViewById(R.id.spCategory);
        subcategory = (Spinner) findViewById(R.id.spSubCategory);
        btnSave = (Button) findViewById(R.id.btnSave);
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), categoriesNames);
        category.setAdapter(customAdapter);
        subcategory.setAdapter(customAdapter);
        companyName = (EditText) findViewById(R.id.etCompanyName);
        address = (EditText) findViewById(R.id.etAddress);
       // linearLayout = (LinearLayout) findViewById(R.id.snakBar);
    }


    private void setuptoolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(null);

        getSupportActionBar().setIcon(R.drawable.home_logo);

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
