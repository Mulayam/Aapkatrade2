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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.Home.registration.RegistrationActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.categories_tab.CategoriesListAdapter;
import com.example.pat.aapkatrade.categories_tab.CategoriesListData;
import com.example.pat.aapkatrade.categories_tab.CategoryListActivity;
import com.example.pat.aapkatrade.general.App_sharedpreference;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Validation;
import com.example.pat.aapkatrade.general.recycleview_custom.MyRecyclerViewEffect;
import com.example.pat.aapkatrade.user_dashboard.addcompany.AddCompany;
import com.example.pat.aapkatrade.user_dashboard.companylist.CompanyList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;


public class MyProfileActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener
{

    Button btnSave, btnEdit, btnLogout;
    public static String shared_pref_name = "aapkatrade";
    App_sharedpreference app_sharedpreference;
    EditText etFName,etLName,etEmail,etMobileNo,etAddress;
    ImageView imgCalender;
    TextView tvDate;



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
        imgCalender = (ImageView)  findViewById(R.id.imgCalender);

        etFName = (EditText) findViewById(R.id.etFName);
        String fname = app_sharedpreference.getsharedpref("username","").toString();
        etFName.setText(fname);
        etFName.setSelection(etFName.getText().length());

        etLName = (EditText) findViewById(R.id.etLName);
        String lname = app_sharedpreference.getsharedpref("lname","").toString();
        etLName.setText(lname);
        etLName.setSelection(etLName.getText().length());

        etEmail = (EditText) findViewById(R.id.etEmail);
        String email = app_sharedpreference.getsharedpref("emailid","").toString();
        etEmail.setText(email);
        etEmail.setSelection(etEmail.getText().length());

        etMobileNo = (EditText) findViewById(R.id.etMobileNo);
        String mobile = app_sharedpreference.getsharedpref("mobile","");
        etMobileNo.setText(mobile);
        etMobileNo.setSelection(etMobileNo.getText().length());

        etAddress = (EditText) findViewById(R.id.etAddress);
        String address = app_sharedpreference.getsharedpref("address","").toString();
        etAddress.setText(address);
        etAddress.setSelection(etAddress.getText().length());

        tvDate = (TextView) findViewById(R.id.tvDate);
        String dob = app_sharedpreference.getsharedpref("dob","").toString();
        tvDate.setText(dob);

        etEmail.setKeyListener(null);

        etMobileNo.setKeyListener(null);

        btnSave = (Button) findViewById(R.id.btnSave);

        btnEdit = (Button) findViewById(R.id.btnEdit);

        btnLogout = (Button) findViewById(R.id.btnlogout);

        /*   imgCalender.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        MyProfileActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "DatePickerDialog");
            }
         });*/


        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String et = etFName.getText().toString();

                System.out.println("et-----------"+et);

                if (!Validation.isEmptyStr(etFName.getText().toString()))
                {

                    if (!Validation.isEmptyStr(etEmail.getText().toString()))
                    {

                        if(Validation.isValidEmail(etEmail.getText().toString()))
                        {

                            if (!Validation.isEmptyStr(etMobileNo.getText().toString()))
                            {

                                if (!Validation.isEmptyStr(etAddress.getText().toString()))
                                {

                                    edit_profile_webservice();

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
            public void onClick(View v)
            {

                Intent i = new Intent(MyProfileActivity.this, AddCompany.class);
                startActivity(i);

            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                save_shared_pref("notlogin", "notlogin", "notlogin");
                Intent Homedashboard = new Intent(MyProfileActivity.this, HomeActivity.class);
                Homedashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(Homedashboard);

            }
        });


    }

    private void edit_profile_webservice()
    {

        Ion.with(MyProfileActivity.this)
                .load("https://aapkatrade.com/slim/my_account")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("name",app_sharedpreference.getsharedpref("username","").toString())
                .setBodyParameter("lastname",app_sharedpreference.getsharedpref("lname","").toString())
                .setBodyParameter("mobile",app_sharedpreference.getsharedpref("mobile","").toString())
                .setBodyParameter("email",app_sharedpreference.getsharedpref("emailid","").toString())
                .setBodyParameter("address",app_sharedpreference.getsharedpref("address","").toString())
                .setBodyParameter("user_id",app_sharedpreference.getsharedpref("userid","").toString())
                .setBodyParameter("user_type",app_sharedpreference.getsharedpref("usertype","").toString())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>()
                {
                    @Override
                    public void onCompleted(Exception e, JsonObject result)
                    {
                        System.out.println("result-----------"+result.toString());

                        if(result == null)
                        {



                        }
                        else
                        {
                            JsonObject jsonObject = result.getAsJsonObject();

                            String message = jsonObject.get("message").getAsString();

                            if(message.toString().equals("updated successfully"))
                            {

                            }
                            else
                            {
                                Toast.makeText(MyProfileActivity.this, "", Toast.LENGTH_SHORT).show();
                            }
                        }
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
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.empty_menu, menu);
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



    public void save_shared_pref(String user_id, String user_name, String email_id)
    {
        app_sharedpreference.setsharedpref("userid", user_id);
        app_sharedpreference.setsharedpref("username", user_name);
        app_sharedpreference.setsharedpref("emailid", email_id);
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth)
    {

        showDate(year, monthOfYear + 1, dayOfMonth);

    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second)
    {

    }

    private void showDate(int year, int month, int day)
    {

        tvDate.setTextColor(getColor(R.color.black));
        tvDate.setText(new StringBuilder().append(year).append("-").append(month).append("-").append(day));
    }




}
