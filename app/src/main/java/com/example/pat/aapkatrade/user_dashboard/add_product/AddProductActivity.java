package com.example.pat.aapkatrade.user_dashboard.add_product;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;

import com.example.pat.aapkatrade.R;


public class AddProductActivity extends AppCompatActivity
{

    Spinner spSubCategory,spCategory,spUnitCategory;

    String[] categoriesNames={"Vegetables","Tailors","Barber","Shoe Repair","Hospitals","Transport","Pharmecy"};

    String[] subcategories = {"Bus","Cabe","Auto","Bikes","E-Rikshwa","Church"};

    String[] spCountryName= {"Afghanistan","Angola","Brazil","Canada","Denmark", "England","France","Germany","India"};

    String[] spStateName= {"Andhra Pradesh","Assam","Bihar","Chandigarh","Chhattisgarh","Delhi","Goa","Gujrat"};

    String[] spCityName= {"Delhi","New Delhi"};



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        setuptoolbar();
        setup_layout();

    }

    private void setup_layout()
    {
        spSubCategory = (Spinner) findViewById(R.id.spSubCategory);

        spCategory = (Spinner) findViewById(R.id.spCategory);

        spUnitCategory = (Spinner) findViewById(R.id.spUnitCategory);

//        SpinnerAdapter customAdapter=new SpinnerAdapter(getApplicationContext(),categoriesNames);
//
//       // SpCountrysAdapter spCountrysAdapter = new SpCountrysAdapter(getApplicationContext(),spCountryName);
//
//       / SpCityAdapter spCityAdapter = new SpCityAdapter(getApplicationContext(),subcategories);
//
//        spSubCategory.setAdapter(customAdapter);
//
//        spCategory.setAdapter(spCityAdapter);
//
//        spUnitCategory.setAdapter(spCountrysAdapter);

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
}
