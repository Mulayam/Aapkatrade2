package com.example.pat.aapkatrade.user_dashboard.addcompany;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.ConnetivityCheck;
import com.example.pat.aapkatrade.general.Validation;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


public class AddCompany extends AppCompatActivity
{

    Button btnSave;
    EditText etCompanyName,etPEmail,etSEmail, etAddress,etDiscription;
    ProgressDialog dialog;
    LinearLayout linearLayout;
    Snackbar snackbar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);

        setuptoolbar();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        initView();

    }

    private void addCompany()
    {

        if(!etCompanyName.getText().toString().equals(""))
        {

            if(!etSEmail.getText().toString().equals(""))
            {

                if(Validation.isValidEmail(etSEmail.getText().toString()))
                {

                    if(!etAddress.getText().toString().equals(""))
                    {

                        if(!etDiscription.getText().toString().equals(""))
                        {

                            if(ConnetivityCheck.isNetworkAvailable(AddCompany.this))
                            {
                                dialog=new ProgressDialog(AddCompany.this);
                                dialog.setMessage("Loading...\nPlease Wait");
                                dialog.setCancelable(false);
                                dialog.setInverseBackgroundForced(false);
                                dialog.show();
                                callAddCompanyWebService("1", etCompanyName.getText().toString(),etPEmail.getText().toString(),etSEmail.getText().toString() ,etAddress.getText().toString(),etDiscription.getText().toString());
                                dialog.hide();
                            }
                            else
                            {
                                linearLayout.setVisibility(View.VISIBLE);
                                snackbar = Snackbar.make(linearLayout, "Please Check Your Network Connection", Snackbar.LENGTH_LONG);
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
                        else
                        {
                            linearLayout.setVisibility(View.VISIBLE);
                            snackbar = Snackbar.make(linearLayout, "Discription is Empty", Snackbar.LENGTH_LONG);
                            snackbar.setActionTextColor(getResources().getColor(android.R.color.holo_red_dark));
                            snackbar.show();

                        }

                    }
                    else
                    {

                        linearLayout.setVisibility(View.VISIBLE);
                        snackbar = Snackbar.make(linearLayout, "Address is Empty", Snackbar.LENGTH_LONG);
                        snackbar.setActionTextColor(getResources().getColor(android.R.color.holo_red_dark));
                        snackbar.show();
                    }


                }
                else
                {
                    linearLayout.setVisibility(View.VISIBLE);
                    snackbar = Snackbar.make(linearLayout, "Please Enter Valid Email", Snackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    snackbar.show();
                }

            }
            else
            {

                linearLayout.setVisibility(View.VISIBLE);
                snackbar = Snackbar.make(linearLayout, "Please Enter Secondary Email", Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(getResources().getColor(android.R.color.holo_red_dark));
                snackbar.show();
            }


        }
        else
        {
            linearLayout.setVisibility(View.VISIBLE);
            snackbar = Snackbar.make(linearLayout, "Please Enter Company Name", Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(getResources().getColor(android.R.color.holo_red_dark));
            snackbar.show();


        }


    }

    private void callAddCompanyWebService(String userId, String companyName,String pEmail , String sEmail, String address, String description)
    {
        Ion.with(AddCompany.this)
                .load("http://aapkatrade.com/slim/addCompany")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("user_id", "1")
                .setBodyParameter("company_name", companyName)
                .setBodyParameter("secondaryEmail",sEmail)
                .setBodyParameter("address", address)
                .setBodyParameter("description", description)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>()
                {
                    @Override
                    public void onCompleted(Exception e, JsonObject result)
                    {

                        JsonObject jsonObject = result.getAsJsonObject();
                        String message = jsonObject.get("message").getAsString();

                        Log.e("message",message);

                        snackbar.make(linearLayout, message, Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    private void initView()
    {
        btnSave = (Button) findViewById(R.id.btnSave);

        etCompanyName = (EditText) findViewById(R.id.etCompanyName);

        etPEmail = (EditText) findViewById(R.id.etPEmail);

        etSEmail= (EditText) findViewById(R.id.etPEmail);

        etAddress = (EditText) findViewById(R.id.etAddress);

        etDiscription = (EditText) findViewById(R.id.etDiscription);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                addCompany();
            }
        });

        linearLayout = (LinearLayout) findViewById(R.id.snakBar);
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
