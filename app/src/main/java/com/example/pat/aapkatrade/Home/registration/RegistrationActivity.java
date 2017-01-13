package com.example.pat.aapkatrade.Home.registration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Spinner;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.user_dashboard.add_product.SpinnerAdapter;

public class RegistrationActivity extends AppCompatActivity
{

    Spinner spBussinessCategory,spCountry,spState,spCity;
    String[] categoriesNames={"India","China","Australia","Portugle","America","New Zealand"};


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        setuptoolbar();
        setup_layout();

    }

    private void setuptoolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Add Products");

    }

    private void setup_layout()
    {

        spBussinessCategory = (Spinner) findViewById(R.id.spBussinessCategory);

        spCountry = (Spinner) findViewById(R.id.spCountryCategory);

        spState = (Spinner) findViewById(R.id.spStateCategory);

        spCity = (Spinner) findViewById(R.id.spCityCategory);

        SpinnerAdapter customAdapter=new SpinnerAdapter(getApplicationContext(),categoriesNames);

        spBussinessCategory.setAdapter(customAdapter);

        spCountry.setAdapter(customAdapter);

        spState.setAdapter(customAdapter);

        spCity.setAdapter(customAdapter);



    }
}
