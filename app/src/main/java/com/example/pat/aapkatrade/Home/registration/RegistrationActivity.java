package com.example.pat.aapkatrade.Home.registration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;

import com.example.pat.aapkatrade.R;


public class RegistrationActivity extends AppCompatActivity
{

    Spinner spBussinessCategory,spCountry,spState,spCity;

    String[] spBussinessName= {"Personal","Business"};

    String[] spCountryName= {"Afghanistan","Angola","Brazil","Canada","Denmark", "England","France","Germany","India"};

    String[] spStateName= {"Andhra Pradesh","Assam","Bihar","Chandigarh","Chhattisgarh","Delhi","Goa","Gujrat"};

    String[] spCityName= {"Delhi","New Delhi"};



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

        getSupportActionBar().setTitle(null);

        getSupportActionBar().setIcon(R.drawable.home_logo);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

        spBussinessCategory = (Spinner) findViewById(R.id.spBussinessCategory);

        spCountry = (Spinner) findViewById(R.id.spCountryCategory);

        spState = (Spinner) findViewById(R.id.spStateCategory);

        spCity = (Spinner) findViewById(R.id.spCityCategory);

        SpBussinessAdapter spBussinessAdapter = new SpBussinessAdapter(getApplicationContext(),spBussinessName);

        SpCountrysAdapter spCountrysAdapter = new SpCountrysAdapter(getApplicationContext(),spCountryName);

        SpStateAdapter spStateAdapter = new SpStateAdapter(getApplicationContext(),spStateName);

        SpCityAdapter spCityAdapter = new SpCityAdapter(getApplicationContext(),spCityName);

        spBussinessCategory.setAdapter(spBussinessAdapter);

        spCountry.setAdapter(spCountrysAdapter);

        spState.setAdapter(spStateAdapter);

        spCity.setAdapter(spCityAdapter);

    }
}
