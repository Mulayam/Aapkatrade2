package com.example.pat.aapkatrade.user_dashboard.editcompany;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.App_sharedpreference;
import com.example.pat.aapkatrade.general.ConnetivityCheck;
import com.example.pat.aapkatrade.general.Validation;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;



public class EditCompanyActivity extends AppCompatActivity
{

    String company_name,creation_date, address,description;
    Button btnSave;
    EditText etCompanyName,etPEmail,etSEmail,etAddress,etDiscription;
    ProgressDialog dialog;
    LinearLayout linearLayout;
    Snackbar snackbar;
    ProgressBarHandler progress_handler;
    App_sharedpreference app_sharedpreference;
    String user_id,email;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_company);

        Intent intent = getIntent();

        company_name = intent.getStringExtra("company_name");

        creation_date = intent.getStringExtra("company_creation_date");

        address  = intent.getStringExtra("address");

        description = intent.getStringExtra("description");

        app_sharedpreference = new App_sharedpreference(getApplicationContext());

        user_id = app_sharedpreference.getsharedpref("userid", "");
        email = app_sharedpreference.getsharedpref("emailid", "");

        progress_handler = new ProgressBarHandler(this);

        setuptoolbar();

        setup_layout();

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

    private void setup_layout() {

        btnSave = (Button) findViewById(R.id.btnSave);

        etCompanyName = (EditText) findViewById(R.id.etCompanyName);
        etCompanyName.setText(company_name);


        etPEmail = (EditText) findViewById(R.id.etPEmail);
        etPEmail.setText(email);


        etSEmail= (EditText) findViewById(R.id.etSEmail);
        etSEmail.setText(email);

        etAddress = (EditText) findViewById(R.id.etAddress);
        etAddress.setText(address);

        etDiscription = (EditText) findViewById(R.id.etDiscription);
        etDiscription.setText(description);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                addCompany();
            }
        });

        linearLayout = (LinearLayout) findViewById(R.id.snakBar);
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

                            if(ConnetivityCheck.isNetworkAvailable(EditCompanyActivity.this))
                            {
                                dialog=new ProgressDialog(EditCompanyActivity.this);
                                dialog.setMessage("Loading...\nPlease Wait");
                                dialog.setCancelable(false);
                                dialog.setInverseBackgroundForced(false);
                                dialog.show();
                                callAddCompanyWebService(user_id, etCompanyName.getText().toString(),etPEmail.getText().toString(),etSEmail.getText().toString() ,etAddress.getText().toString(),etDiscription.getText().toString());
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


    private void callAddCompanyWebService(String userId, final String companyName, String pEmail , String sEmail, String address, String description)
    {

        progress_handler.show();

        Ion.with(EditCompanyActivity.this)
                .load("http://aapkatrade.com/slim/addCompany")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("user_id", userId)
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
                        progress_handler.hide();

                        etCompanyName.setText("");
                        etPEmail.setText("");
                        etSEmail.setText("");
                        etAddress.setText("");
                        etDiscription.setText("");

                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

                      /*  snackbar.make(linearLayout, message, Snackbar.LENGTH_SHORT).show();


                        Intent Homedashboard = new Intent(AddCompany.this, HomeActivity.class);
                        Homedashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(Homedashboard);*/

                    }
                });
    }







}
