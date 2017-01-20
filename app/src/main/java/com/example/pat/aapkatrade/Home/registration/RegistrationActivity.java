package com.example.pat.aapkatrade.Home.registration;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pat.aapkatrade.Home.registration.entity.City;
import com.example.pat.aapkatrade.Home.registration.entity.Country;
import com.example.pat.aapkatrade.Home.registration.entity.State;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpBussinessAdapter;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpCityAdapter;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpCountrysAdapter;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpStateAdapter;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Validation;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;


public class RegistrationActivity extends AppCompatActivity {

    private Spinner spBussinessCategory, spCountry, spState, spCity;
    private String[] spBussinessName = {"-Please Select-", "Personal", "Business"};
    private String[] spCityName = {"-Please Select-", "Delhi", "New Delhi"};
    private EditText etProductName, etFirstName, etLastName, etEmail, etMobileNo, etUserName, etPassword, etReenterPassword;
    private Button btnSave;
    private CoordinatorLayout cl;
    private ProgressDialog dialog;
    private ArrayList<Country> countryList = new ArrayList<>();
    private ArrayList<State> stateList = new ArrayList<>();
    private ArrayList<City> cityList = new ArrayList<>();
    private static  String shared_pref_name = "aapkatrade";
    private SharedPreferences prefs;
    private LinearLayout businessDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registration);
        setuptoolbar();
        setup_layout();
        dialog = ProgressDialog.show(RegistrationActivity.this, "", "Loading. Please wait...", true);
        if(prefs!=null){
            if(prefs.getInt("user",0)==1){
                getCountry();
            }if(prefs.getInt("user",0)==2){
                dialog.hide();
                businessDetails.setVisibility(View.GONE);
            }
        }


    }

    private void getCountry() {
        dialog.show();
        Ion.with(getApplicationContext())
                .load("http://aapkatrade.com/slim/dropdown")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "country")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        JsonObject jsonObject = result.getAsJsonObject();
                        JsonArray jsonResultArray = jsonObject.getAsJsonArray("result");
                        for (int i = 0; i < jsonResultArray.size(); i++) {
                            JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                            Country countryEntity = new Country(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString());
                            countryList.add(countryEntity);
                        }
                        dialog.hide();
                        SpCountrysAdapter spCountrysAdapter = new SpCountrysAdapter(RegistrationActivity.this, countryList);
                        spCountry.setAdapter(spCountrysAdapter);

                        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view,
                                                       int position, long id) {
                                stateList = new ArrayList<State>();
                                getState(countryList.get(position).countryId);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }


                        });
                    }


                });
    }

    public void getState(String countryId) {
        Log.d("data", countryId);
        dialog.show();
        Ion.with(getApplicationContext())
                .load("http://aapkatrade.com/slim/dropdown")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("id", countryId)
                .setBodyParameter("type", "state")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Log.d("data", result.toString());
                        JsonObject jsonObject = result.getAsJsonObject();
                        JsonArray jsonResultArray = jsonObject.getAsJsonArray("result");
                        for (int i = 0; i < jsonResultArray.size(); i++) {
                            JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                            State stateEntity = new State(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString());
                            stateList.add(stateEntity);
//                                            Log.d("data", stateEntity.stateName);
                        }
                        dialog.hide();
                        SpStateAdapter spStateAdapter = new SpStateAdapter(RegistrationActivity.this, stateList);
                        spState.setAdapter(spStateAdapter);

                        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view,
                                                       int position, long id) {
                                cityList = new ArrayList<City>();
                                getCity(stateList.get(position).stateId);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }


                        });
                    }
                });
    }

    public void getCity(String stateId) {
        Log.d("data", stateId);
        dialog.show();
        Ion.with(getApplicationContext())
                .load("http://aapkatrade.com/slim/dropdown")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("id", stateId)
                .setBodyParameter("type", "city")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Log.d("data", result.toString());
                        JsonObject jsonObject = result.getAsJsonObject();
                        JsonArray jsonResultArray = jsonObject.getAsJsonArray("result");
                        for (int i = 0; i < jsonResultArray.size(); i++) {
                            JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                            City cityEntity = new City(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString());
                            cityList.add(cityEntity);
//                                            Log.d("data", stateEntity.stateName);
                        }
                        dialog.hide();
                        SpCityAdapter spCityAdapter = new SpCityAdapter(RegistrationActivity.this, cityList);
                        spCity.setAdapter(spCityAdapter);
                    }
                });
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

    private void setup_layout() {

        cl = (CoordinatorLayout) findViewById(R.id.coordinator_layout);

        spBussinessCategory = (Spinner) findViewById(R.id.spBussinessCategory);

        spCountry = (Spinner) findViewById(R.id.spCountryCategory);

        spState = (Spinner) findViewById(R.id.spStateCategory);

        spCity = (Spinner) findViewById(R.id.spCityCategory);

        btnSave = (Button) findViewById(R.id.btnSave);

        etProductName = (EditText) findViewById(R.id.etProductName);

        etFirstName = (EditText) findViewById(R.id.etFirstName);

        etLastName = (EditText) findViewById(R.id.etLastName);

        etEmail = (EditText) findViewById(R.id.etEmail);

        etMobileNo = (EditText) findViewById(R.id.etMobileNo);

        etUserName = (EditText) findViewById(R.id.etUserName);

        etPassword = (EditText) findViewById(R.id.etPassword);

        businessDetails = (LinearLayout) findViewById(R.id.businessDetails);

        etReenterPassword = (EditText) findViewById(R.id.etReenterPassword);

        SpBussinessAdapter spBussinessAdapter = new SpBussinessAdapter(getApplicationContext(), spBussinessName);

        prefs = getSharedPreferences(shared_pref_name, Activity.MODE_PRIVATE);
        // SpStateAdapter spStateAdapter = new SpStateAdapter(getApplicationContext(), spStateName);

//        SpCityAdapter spCityAdapter = new SpCityAdapter(RegistrationActivity.this, spCityName);

//        spBussinessCategory.setAdapter(spBussinessAdapter);

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


        // spState.setAdapter(spStateAdapter);

//        spCity.setAdapter(spCityAdapter);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Hi Dear", Toast.LENGTH_SHORT).show();
                check_validation();
            }
        });

    }

    private void check_validation() {

        if (etProductName.getText().toString().equals("")) {

            if (etFirstName.getText().toString().equals("")) {

                if (etLastName.getText().toString().equals("")) {

                    if (etEmail.getText().toString().equals("")) {

                        if (Validation.isValidEmail(etEmail.getText().toString())) {

                            if (etMobileNo.getText().toString().equals("")) {

                                if (etMobileNo.getText().toString().length() == 10) {


                                    if (etUserName.getText().toString().equals("")) {

                                        if (etPassword.getText().toString().equals("")) {


                                            if (etReenterPassword.getText().toString().equals("")) {


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


                                } else {

                                    showmessage("Please Enter 10 Digit Mobile Number");

                                }

                            }

                        } else {

                            showmessage("Please Enter Valid Email Address");


                        }

                    } else {

                        showmessage("Please Enter Email");

                    }


                } else {

                    showmessage("Please Enter Last Name");

                }


            } else {

                showmessage("Please Enter First Name");

            }


        } else {

            showmessage("Please Enter Shop Name");

        }


    }

    public void showmessage(String message) {

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
