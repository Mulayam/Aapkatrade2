package com.example.pat.aapkatrade.Home.registration;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Validation;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


public class RegistrationActivity extends AppCompatActivity
{

    Spinner spBussinessCategory,spCountry,spState,spCity;

    String[] spBussinessName= {"-Please Select-","Personal","Business"};

    String[] spCountryName= {"-Please Select-","Afghanistan","Angola","Brazil","Canada","Denmark", "England","France","Germany","India"};

    String[] spStateName= {"-Please Select-","Andhra Pradesh","Assam","Bihar","Chandigarh","Chhattisgarh","Delhi","Goa","Gujrat"};

    String[] spCityName= {"-Please Select-","Delhi","New Delhi"};

    EditText etProductName,etFirstName,etLastName,etEmail,etMobileNo,etUserName,etPassword,etReenterPassword;
    Button btnSave;
    CoordinatorLayout cl;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registration);

        setuptoolbar();

        setup_layout();

       // getCountry();

    }

    private void getCountry()
    {
        Ion.with(getApplicationContext())
                .load("http://aapkatrade.com/slim/countries")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "country")
                .setBodyParameter("id", "101")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>()
                {
                    @Override
                    public void onCompleted(Exception e, JsonObject result)
                    {

                        JsonObject jsonObject = result.getAsJsonObject();
                        Log.d("data",result.toString());
                       // System.out.println("result--------"+result.toString());
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

    private void setup_layout()
    {

        cl = (CoordinatorLayout) findViewById(R.id.coordinator_layout) ;

        spBussinessCategory = (Spinner) findViewById(R.id.spBussinessCategory);

        spCountry = (Spinner) findViewById(R.id.spCountryCategory);

        spState = (Spinner) findViewById(R.id.spStateCategory);

        spCity = (Spinner) findViewById(R.id.spCityCategory);

        btnSave = (Button) findViewById(R.id.btnSave);

        etProductName = (EditText)  findViewById(R.id.etProductName);

        etFirstName = (EditText) findViewById(R.id.etFirstName);

        etLastName  = (EditText) findViewById(R.id.etLastName);

        etEmail = (EditText) findViewById(R.id.etEmail);

        etMobileNo = (EditText) findViewById(R.id.etMobileNo);

        etUserName = (EditText) findViewById(R.id.etUserName);

        etPassword = (EditText) findViewById(R.id.etPassword);

        etReenterPassword = (EditText) findViewById(R.id.etReenterPassword);

        SpBussinessAdapter spBussinessAdapter = new SpBussinessAdapter(getApplicationContext(),spBussinessName);

        SpCountrysAdapter spCountrysAdapter = new SpCountrysAdapter(getApplicationContext(),spCountryName);

        SpStateAdapter spStateAdapter = new SpStateAdapter(getApplicationContext(),spStateName);

        SpCityAdapter spCityAdapter = new SpCityAdapter(getApplicationContext(),spCityName);

        spBussinessCategory.setAdapter(spBussinessAdapter);

       /* spBussinessCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                if (spBussinessCategory.getSelectedItem().equals("Personal"))
                {

                    etProductName.setVisibility(View.VISIBLE);

                }
                else if(spBussinessCategory.getSelectedItem().equals("Business"))
                {

                    etProductName.setVisibility(View.VISIBLE);

                }
                else
                {

                    etProductName.setVisibility(View.GONE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });*/

        spCountry.setAdapter(spCountrysAdapter);

        spState.setAdapter(spStateAdapter);

        spCity.setAdapter(spCityAdapter);

        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Toast.makeText(getApplicationContext(),"Hi Dear",Toast.LENGTH_SHORT).show();
               check_validation();
            }
        });

    }

    private void check_validation()
    {

                        if(etProductName.getText().toString().equals(""))
                        {

                            if(etFirstName.getText().toString().equals(""))
                            {

                                if(etLastName.getText().toString().equals(""))
                                {

                                    if(etEmail.getText().toString().equals(""))
                                    {

                                        if(Validation.isValidEmail(etEmail.getText().toString()))
                                        {

                                            if(etMobileNo.getText().toString().equals(""))
                                            {

                                                if(etMobileNo.getText().toString().length() == 10)
                                                {


                                                    if(etUserName.getText().toString().equals(""))
                                                    {

                                                        if(etPassword.getText().toString().equals(""))
                                                        {


                                                            if(etReenterPassword.getText().toString().equals(""))
                                                            {



                                                            }
                                                            {

                                                                showmessage("Please ReEnter Password");

                                                            }


                                                        }
                                                        {

                                                            showmessage("Please Enter Password");

                                                        }

                                                    }
                                                    {

                                                        showmessage("Please Enter User Name");

                                                    }


                                                }
                                                else
                                                {

                                                    showmessage("Please Enter 10 Digit Mobile Number");

                                                }

                                            }

                                        }
                                        else
                                        {

                                            showmessage("Please Enter Valid Email Address");


                                        }

                                    }
                                    else
                                    {

                                        showmessage("Please Enter Email");

                                    }



                                }
                                else
                                {

                                    showmessage("Please Enter Last Name");

                                }


                            }
                            else
                            {

                                showmessage("Please Enter First Name");

                            }


                        }
                        else
                        {

                            showmessage("Please Enter Shop Name");

                        }


    }

    public void showmessage(String message)
    {

        Snackbar snackbar = Snackbar
                .make(cl, message, Snackbar.LENGTH_LONG)
                .setAction("", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                    Snackbar snackbar1 = Snackbar.make(cl, "", Snackbar.LENGTH_SHORT);
//                    snackbar1.show();
                    }
                });



    }



}
