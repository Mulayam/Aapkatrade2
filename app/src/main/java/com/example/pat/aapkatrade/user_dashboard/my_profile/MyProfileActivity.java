package com.example.pat.aapkatrade.user_dashboard.my_profile;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.App_sharedpreference;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Validation;
import com.example.pat.aapkatrade.user_dashboard.addcompany.AddCompany;
import com.example.pat.aapkatrade.user_dashboard.companylist.CompanyList;


public class MyProfileActivity extends AppCompatActivity
{

    Button btnsave, btnEdit, btnLogout;
    public static String shared_pref_name = "aapkatrade";
    App_sharedpreference app_sharedpreference;
    EditText etFName,etLName,etEmail,etMobileNo,etAddress;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        app_sharedpreference = new App_sharedpreference(this);
        setuptoolbar();
        setup_layout();


    }

    private void setup_layout()
    {

        etFName = (EditText) findViewById(R.id.etFName);

        etLName = (EditText) findViewById(R.id.etLName);

        etEmail = (EditText) findViewById(R.id.etEmail);

        etMobileNo = (EditText) findViewById(R.id.etMobileNo);

        etAddress = (EditText) findViewById(R.id.etAddress);

        btnsave = (Button) findViewById(R.id.btnSave);

        btnEdit = (Button) findViewById(R.id.btnEdit);

        btnLogout = (Button) findViewById(R.id.btnlogout);


        btnsave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                 if (Validation.isEmptyStr(etFName.getText().toString()))
                 {

                     if (Validation.isEmptyStr(etEmail.getText().toString()))
                     {

                         if(Validation.isValidEmail(etEmail.getText().toString()))
                         {

                             if (Validation.isEmptyStr(etMobileNo.getText().toString()))
                             {

                                 if (Validation.isEmptyStr(etAddress.getText().toString()))
                                 {


                                     Intent i = new Intent(MyProfileActivity.this, CompanyList.class);
                                     startActivity(i);

                                 }
                                 else
                                 {

                                     Toast.makeText(getApplicationContext(),"Please Enter Address",Toast.LENGTH_SHORT).show();

                                 }


                             }
                             else
                             {
                                 Toast.makeText(getApplicationContext(),"Please Enter Mobile Number",Toast.LENGTH_SHORT).show();
                             }

                         }
                         else
                         {
                             Toast.makeText(getApplicationContext(),"Please Enter Valid Email Address",Toast.LENGTH_SHORT).show();
                         }


                     }
                     else
                     {

                         Toast.makeText(getApplicationContext(),"Please Enter Email Address",Toast.LENGTH_SHORT).show();
                     }

                 }
                  else
                 {

                     Toast.makeText(getApplicationContext(),"Please Enter First Name",Toast.LENGTH_SHORT).show();

                 }



            }
        });



        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyProfileActivity.this, AddCompany.class);
                startActivity(i);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_shared_pref("notlogin", "notlogin", "notlogin");
                Intent Homedashboard = new Intent(MyProfileActivity.this, HomeActivity.class);
                Homedashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(Homedashboard);
            }
        });


    }


    private void setuptoolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        //  getSupportActionBar().setIcon(R.drawable.home_logo);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.empty_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public void save_shared_pref(String user_id, String user_name, String email_id)
    {
        app_sharedpreference.setsharedpref("userid", user_id);
        app_sharedpreference.setsharedpref("username", user_name);
        app_sharedpreference.setsharedpref("emailid", email_id);

    }


}
