package com.example.pat.aapkatrade.user_dashboard.add_product;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Validation;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class AddProductActivity extends AppCompatActivity
{

    Spinner spSubCategory,spCategory,spUnitCategory;
    EditText product_delivery_location,product_name;
    Button Add_product;


String selected_productname,selectcategory,selected_subcategory,selected_unit;




    String[] categoriesNames={"Vegetables","Tailors","Barber","Shoe Repair","Hospitals","Transport","Pharmecy"};

    String[] subcategories = {"Bus","Cabe","Auto","Bikes","E-Rikshwa","Church"};

    String[] units= {"kg","gm","quantity"};

    String[] spStateName= {"Andhra Pradesh","Assam","Bihar","Chandigarh","Chhattisgarh","Delhi","Goa","Gujrat"};

    String[] spCityName= {"Delhi","New Delhi"};



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        setuptoolbar();
        setup_layout();

        product_delivery_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent =
                            new PlaceAutocomplete
                                    .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(AddProductActivity.this);
                    startActivityForResult(intent, 1);
                } catch (GooglePlayServicesRepairableException e) {
                    Log.e("GooglePlayServices",e.toString());
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    Log.e("GooglePlayServices_not",e.toString());
                    // TODO: Handle the error.
                }
            }
        });


    }

    private void setup_layout()
    {

        product_name=(EditText)findViewById(R.id.etProductName);
        spSubCategory = (Spinner) findViewById(R.id.spSubCategory);

        spCategory = (Spinner) findViewById(R.id.spCategory);

        //spUnitCategory = (Spinner) findViewById(R.id.spUnitCategory);
        product_delivery_location=(EditText)findViewById(R.id.etDeliver_location);

        Add_product=(Button)findViewById(R.id.btnUpload);



        Add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Validation.validate_edittext(product_name))
                {
                    if(Validation.validate_edittext(product_delivery_location))
                    {




                    }
                    else{
                        product_delivery_location.setError("Invalid Field");
                    }
                }
                else{
                    product_name.setError("Invalid Field");
                }
            }
        });




        SpinnerAdapter customAdapter=new SpinnerAdapter(getApplicationContext(),categoriesNames);
        SpinnerAdapter spsubcategory=new SpinnerAdapter(getApplicationContext(),subcategories);
        //SpinnerAdapter spunites=new SpinnerAdapter(getApplicationContext(),units);

       // SpCountrysAdapter spCountrysAdapter = new SpCountrysAdapter(getApplicationContext(),spCountryName);

       // SpCityAdapter spCityAdapter = new SpCityAdapter(getApplicationContext(),subcategories);

        spSubCategory.setAdapter(spsubcategory );

        spCategory.setAdapter(customAdapter);
        //spUnitCategory.setAdapter(spunites);
       // spUnitCategory.setAdapter(spCountrysAdapter);

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




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // retrive the data by using getPlace() method.
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.e("Tag", "Place: " + place.getAddress()  +place.getLatLng());
              Geocoder mGeocoder = new Geocoder(AddProductActivity.this, Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = mGeocoder.getFromLocation(place.getLatLng().latitude,place.getLatLng().longitude, 1);

                    String cityName = addresses.get(0).getAddressLine(0);
                    String stateName = addresses.get(0).getAddressLine(1);
                    String countryName = addresses.get(0).getAddressLine(2);
                    String countryName2 = addresses.get(0).getAddressLine(3);
                    product_delivery_location.setText(place.getName()+",\n"+
                            place.getAddress());
                    Log.e("cityName",cityName);
                    Log.e("stateName",stateName);
                    Log.e("countryName",countryName);
                   // Log.e("countryName2",countryName2);



                } catch (IOException e) {
                    Log.e("IOException",e.toString());
                }



            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.e("Tag", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }



}
