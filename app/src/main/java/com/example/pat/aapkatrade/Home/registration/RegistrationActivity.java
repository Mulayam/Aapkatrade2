package com.example.pat.aapkatrade.Home.registration;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.registration.entity.BuyerRegistration;
import com.example.pat.aapkatrade.Home.registration.entity.City;
import com.example.pat.aapkatrade.Home.registration.entity.Country;
import com.example.pat.aapkatrade.Home.registration.entity.SellerRegistration;
import com.example.pat.aapkatrade.Home.registration.entity.State;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpBussinessAdapter;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpCityAdapter;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpCountrysAdapter;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpStateAdapter;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.App_config;
import com.example.pat.aapkatrade.general.App_sharedpreference;
import com.example.pat.aapkatrade.general.Call_webservice;
import com.example.pat.aapkatrade.general.TaskCompleteReminder;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Utils.ImageUtils;
import com.example.pat.aapkatrade.general.Validation;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.login.ActivityOTPVerify;
import com.example.pat.aapkatrade.utils.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class RegistrationActivity extends AppCompatActivity {
    private static SellerRegistration formSellerData = new SellerRegistration();
    private static BuyerRegistration formBuyerData = new BuyerRegistration();
    private int isAllFieldSet = 0;
    private Spinner spBussinessCategory, spCountry, spState, spCity;
    private String[] spBussinessName = {"-Please Select Business Type-", "Licence", "Personal"};
    private EditText etProductName, etFirstName, etLastName, etDOB, etEmail, etMobileNo, etUserName, etAddress, etPassword, etReenterPassword;
    private TextView tvSave;
    private LinearLayout registrationLayout;
    private ProgressDialog dialog;
    private ArrayList<Country> countryList = new ArrayList<>();
    private ArrayList<State> stateList = new ArrayList<>();
    private ArrayList<City> cityList = new ArrayList<>();
    private LinearLayout businessDetails, uploadView;
    private static final int reqCode = 33;
    private boolean isReqCode = false;
    private ImageView uploadImage, openCalander, cancelImage;
    App_sharedpreference app_sharedpreference;
    private CircleImageView circleImageView;
    private Bitmap imageForPreview;
    HashMap<String, String> webservice_header_type = new HashMap<>();
    private String busiType = "", countryID, stateID, cityID;
    private RelativeLayout spBussinessCategoryLayout, previewImageLayout, dobLayout;
    private DatePickerDialog datePickerDialog;
    ProgressBarHandler progressBarHandler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        app_sharedpreference = new App_sharedpreference(RegistrationActivity.this);
        setuptoolbar();
        initView();
        setProgressDialogue();
        saveUserTypeInSharedPreferences();

        setUpBusinessCategory();
        getCountry();
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picPhoto();
            }
        });
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("reach", "reach");
                if (app_sharedpreference.shared_pref != null) {

                    Log.e("reach", "reach1");
                    /*
                    Seller Registration
                     */
                    if (app_sharedpreference.getsharedpref("usertype", "0").equals("1")) {

                        Log.e("reach", "reach2");
                        setSellerFormData();
                        validateFields(String.valueOf(1));
                        if (isAllFieldSet > 0)
                            callWebServiceForSellerRegistration();
                        }
                        else{
                            callWebServiceForSellerRegistration();
                            Log.e("isAllFieldSet",isAllFieldSet+"");
                        }
                    }
                    /*
                    Buyer Registration
                     */
                    else if (app_sharedpreference.getsharedpref("usertype", "0").equals("2")) {

                        Log.e("reach", "reach3");
                        getBuyerFormData();
                        validateFields(String.valueOf(2));
                        if (isAllFieldSet > 0)
                            callWebServiceForBuyerRegistration();
                    }
                }


        });

        openCalander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar newCalendar = Calendar.getInstance();
                datePickerDialog = new DatePickerDialog(RegistrationActivity.this, R.style.myCalTheme, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        showDate(year, month + 1, day);
                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        cancelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previewImageLayout.setVisibility(View.GONE);
            }
        });


    }


    private void showDate(int year, int month, int day) {
        etDOB.setTextColor(getColor(R.color.black));
        etDOB.setText(new StringBuilder().append(year).append("-").append(month).append("-").append(day));
    }

    private void setProgressDialogue() {
        dialog = new ProgressDialog(RegistrationActivity.this);
        dialog.setMessage("Please Wait\nLoading.....");
    }

    private void saveUserTypeInSharedPreferences() {
        if (app_sharedpreference != null) {
            if (app_sharedpreference.getsharedpref("usertype", "0").equals("1")) {
                etAddress.setVisibility(View.GONE);
                Log.e("user", "user");
            }
            if (app_sharedpreference.getsharedpref("usertype", "0").equals("2")) {
                Log.e("user2", "user2");
                uploadView.setVisibility(View.GONE);
                spBussinessCategoryLayout.setVisibility(View.GONE);
                etProductName.setVisibility(View.GONE);
                dobLayout.setVisibility(View.GONE);

            }
        } else {
            Log.e("user3", "user3");
        }
    }


    private void callWebServiceForSellerRegistration() {
        progressBarHandler.show();

        Log.e("reach", getBusiType(formSellerData.getBusinessType()) + " Seller Data--------->\n" + formSellerData.toString());
        Ion.with(RegistrationActivity.this)
                .load("http://aapkatrade.com/slim/sellerregister")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "2")
                .setBodyParameter("business_type", getBusiType(formSellerData.getBusinessType()))
                .setBodyParameter("companyname", formSellerData.getCompanyName())
                .setBodyParameter("name", formSellerData.getFirstName())
                .setBodyParameter("lastname", formSellerData.getLastName())
                .setBodyParameter("dob", formSellerData.getDOB())
                .setBodyParameter("mobile", formSellerData.getMobile())
                .setBodyParameter("email", formSellerData.getEmail())
                .setBodyParameter("password", formSellerData.getPassword())
                .setBodyParameter("country_id", formSellerData.getCountryId())
                .setBodyParameter("state_id", formSellerData.getStateId())
                .setBodyParameter("city_id", formSellerData.getCityId())
                .setBodyParameter("client_id", formSellerData.getClientId())
                .setBodyParameter("shopname", formSellerData.getShopName())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        Log.e("data", result.toString());
                        if (result.get("error").getAsString().equals("false")) {
                            Log.d("registration_seller", "done");
                            progressBarHandler.hide();
                            startActivity(new Intent(RegistrationActivity.this, ActivityOTPVerify.class));
                        }
                    }

                });
    }


    private void callWebServiceForBuyerRegistration() {
        progressBarHandler.show();
        Ion.with(RegistrationActivity.this)
                .load("http://aapkatrade.com/slim/buyerregister")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "1")
                .setBodyParameter("country_id", formBuyerData.getCountryId())
                .setBodyParameter("state_id", formBuyerData.getStateId())
                .setBodyParameter("city_id", formBuyerData.getCityId())
                .setBodyParameter("address", formBuyerData.getAddress())
                .setBodyParameter("name", formBuyerData.getFirstName())
                .setBodyParameter("lastname", formBuyerData.getLastName())
                .setBodyParameter("email", formBuyerData.getEmail())
                .setBodyParameter("mobile", formBuyerData.getMobile())
                .setBodyParameter("password", formBuyerData.getPassword())
                .setBodyParameter("client_id", formBuyerData.getClientId())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        Log.e("data", result.toString());
                        if (result.get("error").getAsString().equals("false")) {
                            Log.d("registration_buyer", "done");
                            progressBarHandler.hide();
                            startActivity(new Intent(RegistrationActivity.this, ActivityOTPVerify.class));
                        }
                    }

                });
    }


    private void setUpBusinessCategory() {
        spBussinessCategory.setAdapter(new SpBussinessAdapter(getApplicationContext(), spBussinessName));
        spBussinessCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                busiType = spBussinessName[position];
                if (spBussinessName[position].equalsIgnoreCase("Personal")) {
                    uploadView.setVisibility(View.GONE);
                    etProductName.setHint(getString(R.string.shop_name));
                } else if (spBussinessName[position].equalsIgnoreCase("Licence")) {
                    uploadView.setVisibility(View.VISIBLE);
                    etProductName.setHint(getString(R.string.company_name_heading));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getCountry() {
        HashMap<String, String> webservice_body_parameter = new HashMap<>();
        webservice_body_parameter.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        webservice_body_parameter.put("type", "country");

        HashMap<String, String> webservice_header_type = new HashMap<>();
        webservice_header_type.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");


        Call_webservice.getcountrystatedata(RegistrationActivity.this, "country", getResources().getString(R.string.webservice_base_url) + "/dropdown", webservice_body_parameter, webservice_header_type);

        Call_webservice.taskCompleteReminder = new TaskCompleteReminder() {
            @Override
            public void Taskcomplete(JsonObject webservice_returndata) {



                if (webservice_returndata != null) {
                    Log.e("webservice_returndata", webservice_returndata.toString());
                    JsonObject jsonObject = webservice_returndata.getAsJsonObject();
                    JsonArray jsonResultArray = jsonObject.getAsJsonArray("result");
                    countryList.clear();
                    Country countryEntity_init = new Country("-1", "-Please Select Country-");
                    countryList.add(countryEntity_init);
                    for (int i = 0; i < jsonResultArray.size(); i++) {

                        JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);

                        Country countryEntity = new Country(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString());
                        countryList.add(countryEntity);
                    }
                    SpCountrysAdapter spCountrysAdapter = new SpCountrysAdapter(RegistrationActivity.this, countryList);
                    spCountry.setAdapter(spCountrysAdapter);
                    spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Log.d("datacountry", countryList.get(position).countryId);
                            countryID = countryList.get(position).countryId;
                            stateList = new ArrayList<>();
                            if (position > 0) {
                                getState(countryList.get(position).countryId);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }


                    });
                } else {
                    AndroidUtils.showSnackBar(registrationLayout, "Country Not Found");
                }

            }
        };

    }

    public void getState(String countryId) {


        HashMap<String, String> webservice_body_parameter = new HashMap<>();
        webservice_body_parameter.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        webservice_body_parameter.put("type", "state");
        webservice_body_parameter.put("id", countryId);

        dialog.show();
        Call_webservice.getcountrystatedata(RegistrationActivity.this, "state", getResources().getString(R.string.webservice_base_url) + "/dropdown", webservice_body_parameter, webservice_header_type);

        Call_webservice.taskCompleteReminder = new TaskCompleteReminder() {
            @Override
            public void Taskcomplete(JsonObject state_data_webservice) {
                if (state_data_webservice == null) {
                    JsonObject jsonObject = state_data_webservice.getAsJsonObject();
                    JsonArray jsonResultArray = jsonObject.getAsJsonArray("result");
                    stateList.clear();
                    State stateEntity_init = new State("-1", "-Pleas Select State-");
                    stateList.add(stateEntity_init);

                    for (int i = 0; i < jsonResultArray.size(); i++) {
                        JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                        State stateEntity = new State(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString());
                        stateList.add(stateEntity);
                    }
                    dialog.hide();
                    SpStateAdapter spStateAdapter = new SpStateAdapter(RegistrationActivity.this, stateList);
                    spState.setAdapter(spStateAdapter);

                    spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {
                            stateID = stateList.get(position).stateId;
                            cityList = new ArrayList<>();
                            if (position > 0) {
                                getCity(stateList.get(position).stateId);
                            }
//                        if (!(Integer.parseInt(stateID) > 0)) {
//                            showmessage("Please Select State");
//                        }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }


                    });
                } else {
                    AndroidUtils.showSnackBar(registrationLayout, "State Not Found");
                }
            }

        };
    }


    //
    public void getCity(String stateId) {

        HashMap<String, String> webservice_body_parameter = new HashMap<>();
        webservice_body_parameter.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        webservice_body_parameter.put("type", "city");
        webservice_body_parameter.put("id", stateId);

        dialog.show();
        Call_webservice.getcountrystatedata(RegistrationActivity.this, "city", getResources().getString(R.string.webservice_base_url) + "/dropdown", webservice_body_parameter, webservice_header_type);

        Call_webservice.taskCompleteReminder = new TaskCompleteReminder() {
            @Override
            public void Taskcomplete(JsonObject city_data_webservice) {
                if (city_data_webservice != null) {
                    JsonObject jsonObject = city_data_webservice.getAsJsonObject();
                    JsonArray jsonResultArray = jsonObject.getAsJsonArray("result");

                    City cityEntity_init = new City("-1", "-Please Select City-");
                    cityList.add(cityEntity_init);

                    for (int i = 0; i < jsonResultArray.size(); i++) {
                        JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                        City cityEntity = new City(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString());
                        cityList.add(cityEntity);
                    }

                    dialog.hide();
                    SpCityAdapter spCityAdapter = new SpCityAdapter(RegistrationActivity.this, cityList);
                    spCity.setAdapter(spCityAdapter);

                    spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            cityID = cityList.get(position).cityId;
//                        if (!(Integer.parseInt(cityID) > 0)) {
//                            showmessage("Please Select City");
//                        }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else {
                    AndroidUtils.showSnackBar(registrationLayout, "No City Found");
                }
            }
        };
    }


    private void setuptoolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
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

    private void initView() {
        progressBarHandler=new ProgressBarHandler(this);
        registrationLayout = (LinearLayout) findViewById(R.id.registrationLayout);
        spBussinessCategory = (Spinner) findViewById(R.id.spBussinessCategory);
        spCountry = (Spinner) findViewById(R.id.spCountryCategory);
        spState = (Spinner) findViewById(R.id.spStateCategory);
        spCity = (Spinner) findViewById(R.id.spCityCategory);
        tvSave = (TextView) findViewById(R.id.tvSave);
        etProductName = (EditText) findViewById(R.id.etshopname);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etDOB = (EditText) findViewById(R.id.etDOB);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etMobileNo = (EditText) findViewById(R.id.etMobileNo);
        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etAddress = (EditText) findViewById(R.id.etAddress);
        businessDetails = (LinearLayout) findViewById(R.id.businessDetails);
        spBussinessCategoryLayout = (RelativeLayout) findViewById(R.id.spBussinessCategoryLayout);
        etReenterPassword = (EditText) findViewById(R.id.etReenterPassword);
        uploadView = (LinearLayout) findViewById(R.id.uploadView);
        circleImageView = (CircleImageView) findViewById(R.id.previewImage);
        uploadImage = (ImageView) findViewById(R.id.uploadButton);
        openCalander = (ImageView) findViewById(R.id.openCalander);
        previewImageLayout = (RelativeLayout) findViewById(R.id.previewImageLayout);
        cancelImage = (ImageView) findViewById(R.id.cancelImage);
        dobLayout = (RelativeLayout) findViewById(R.id.dobLayout);
        webservice_header_type.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");


    }

    private void validateFields(String userType) {
        isAllFieldSet = 0;
        Log.e("reach", "validateFiledsCalled");
        if (userType.equals("1")) {
            if (formSellerData != null) {

                Log.e("reach", formSellerData.toString() + "            DATAAAAAAAAA");
                if (Validation.isEmptyStr(formSellerData.getBusinessType())
                        || formSellerData.getBusinessType().equals(spBussinessName[0])) {
//                    Log.e("reach", formSellerData.getBusinessType()+"))))))))"+busiType+"\n(((((("+formSellerData.toString());
                    showmessage("Please Select Business Category");
                    isAllFieldSet++;
                } else if (Validation.isEmptyStr(etProductName.getText().toString())) {
                    putError(12);
                    isAllFieldSet++;
                } else if (!(Validation.isNonEmptyStr(formSellerData.getCountryId()) &&
                        Integer.parseInt(formSellerData.getCountryId()) > 0)) {

                    Log.e("reach", formSellerData.getCountryId() + "            DATAAAAAAAAA" + !(Validation.isEmptyStr(formSellerData.getCountryId()) ||
                            Integer.parseInt(formSellerData.getCountryId()) > 0));
                    showmessage("Please Select Country");
                    isAllFieldSet++;
                } else if (!(Validation.isNonEmptyStr(formSellerData.getStateId()) &&
                        Integer.parseInt(formSellerData.getStateId()) > 0)) {
                    showmessage("Please Select State");
                    isAllFieldSet++;
                } else if (!(Validation.isNonEmptyStr(formSellerData.getCityId()) &&
                        Integer.parseInt(formSellerData.getCityId()) > 0)) {
                    showmessage("Please Select City");
                    isAllFieldSet++;
                } else if (Validation.isEmptyStr(formSellerData.getFirstName())) {
                    putError(0);
                    isAllFieldSet++;
                } else if (Validation.isEmptyStr(formSellerData.getLastName())) {
                    putError(1);
                    isAllFieldSet++;
                } else if (!Validation.isValidDOB(formSellerData.getDOB())) {
                    putError(6);
                    isAllFieldSet++;
                } else if (!Validation.isValidEmail(formSellerData.getEmail())) {
                    putError(2);
                    isAllFieldSet++;
                } else if (!Validation.isValidNumber(formSellerData.getMobile(), Validation.getNumberPrefix(formSellerData.getMobile()))) {
                    putError(3);
                    isAllFieldSet++;
                } else if (Validation.isEmptyStr(etUserName.getText().toString())) {
                    putError(10);
                    isAllFieldSet++;
                } else if (!Validation.isValidPassword(formSellerData.getPassword())) {
                    putError(4);
                    isAllFieldSet++;
                } else if (!Validation.isPasswordMatching(formSellerData.getPassword(), formSellerData.getConfirmPassword())) {
                    putError(5);
                    isAllFieldSet++;
                }
            }
            Log.d("error", "error Null");
        }
        if (userType.equals("2")) {
            Log.e("reach", "BuyerValidate Called");
            if (formBuyerData != null) {
                if (!(Validation.isNonEmptyStr(formBuyerData.getCountryId()) &&
                        Integer.parseInt(formBuyerData.getCountryId()) > 0)) {
                    showmessage("Please Select Country");
                    isAllFieldSet++;
                } else if (!(Validation.isNonEmptyStr(formBuyerData.getStateId()) &&
                        Integer.parseInt(formBuyerData.getStateId()) > 0)) {
                    showmessage("Please Select State");
                    isAllFieldSet++;
                } else if (!(Validation.isEmptyStr(formBuyerData.getCityId()) ||
                        Integer.parseInt(formBuyerData.getCityId()) > 0)) {
                    showmessage("Please Select City");
                    isAllFieldSet++;
                } else if (Validation.isEmptyStr(formBuyerData.getAddress())) {
                    putError(9);
                    isAllFieldSet++;
                } else if (Validation.isEmptyStr(formBuyerData.getFirstName())) {
                    putError(0);
                    isAllFieldSet++;
                } else if (Validation.isEmptyStr(formBuyerData.getLastName())) {
                    putError(1);
                    isAllFieldSet++;
                } else if (!Validation.isValidEmail(formBuyerData.getEmail())) {
                    putError(2);
                    isAllFieldSet++;
                } else if (!Validation.isValidNumber(formBuyerData.getMobile(), Validation.getNumberPrefix(formBuyerData.getMobile()))) {
                    putError(3);
                    isAllFieldSet++;
                } else if (!Validation.isEmptyStr(formBuyerData.getUserName())) {
                    putError(10);
                    isAllFieldSet++;
                } else if (!Validation.isValidPassword(formBuyerData.getPassword())) {
                    putError(4);
                    isAllFieldSet++;
                } else if (!Validation.isPasswordMatching(formBuyerData.getPassword(), formBuyerData.getConfirmPassword())) {
                    putError(5);
                    isAllFieldSet++;
                }

            }
        }


    }

    private void putError(int id) {
        Log.e("reach", "       )))))))))" + id);
        switch (id) {
            case 0:
                etFirstName.setError("First Name Can't be empty");
                break;
            case 1:
                etLastName.setError("Last Name Can't be empty");
                break;
            case 2:
                etEmail.setError("Please Enter Valid Email");
                break;
            case 3:
                etMobileNo.setError("Please Enter Valid Mobile Number");
                break;
            case 4:
                etPassword.setError("Password must be greater than 6 digits");
                break;
            case 5:
                etReenterPassword.setError("Password did not matched");
                break;
            case 6:
                etDOB.setError("Please Select Date");
            case 9:
                etAddress.setError("Address Can't be empty");
                break;
            case 10:
                etUserName.setError("Please Enter Valid UserName");
                break;
            case 12:
                if (etProductName.getHint().toString().equals("Shop Name*")) {
                    etProductName.setError("Please Enter Shop Name");
                } else if (etProductName.getHint().toString().equals("Company Name*")) {
                    etProductName.setError("Please Enter Company Name");
                }
                break;

            default:
                break;
        }
    }

    public void showmessage(String message) {

        Snackbar snackbar = Snackbar
                .make(registrationLayout, message, Snackbar.LENGTH_SHORT)
                .setAction("", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                    Snackbar snackbar1 = Snackbar.make(cl, "", Snackbar.LENGTH_SHORT);
//                    snackbar1.show();
                    }
                });
        snackbar.show();


    }

    void picPhoto() {
        String str[] = new String[]{"Camera", "Gallery"};
        new AlertDialog.Builder(this).setItems(str,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        performImgPicAction(which);
                    }
                }).show();
    }

    void performImgPicAction(int which) {
        Intent in;
        if (which == 1) {
            in = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        } else {
            in = new Intent();
            in.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        startActivityForResult(Intent.createChooser(in, "Select profile picture"), which);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        try {
            if (reqCode == requestCode) {
                if (resultCode == Activity.RESULT_OK) {
                    isReqCode = true;
                }
            } else if (resultCode == Activity.RESULT_OK) {
                BitmapFactory.Options option = new BitmapFactory.Options();
                option.inDither = false;
                option.inPurgeable = true;
                option.inInputShareable = true;
                option.inTempStorage = new byte[32 * 1024];
                option.inPreferredConfig = Bitmap.Config.RGB_565;
                if (Build.VERSION.SDK_INT < 19) {
                    Uri selectedImageURI = data.getData();

                    imageForPreview = BitmapFactory.decodeFile(getFilesDir().getPath(), option);
                } else {
                    if (data.getData() != null) {

                        ParcelFileDescriptor pfd;
                        try {
                            pfd = getContentResolver()
                                    .openFileDescriptor(data.getData(), "r");
                            if (pfd != null) {
                                FileDescriptor fileDescriptor = pfd
                                        .getFileDescriptor();

                                imageForPreview = BitmapFactory.decodeFileDescriptor(
                                        fileDescriptor, null, option);
                            }
                            pfd.close();


                        } catch (FileNotFoundException e) {
                            Log.e("FileNotFoundException", e.toString());
                        } catch (IOException e) {
                            Log.e("IOException", e.toString());
                        }
                    } else {
                        imageForPreview = (Bitmap) data.getExtras().get("data");
                        Log.e("data_not_found", "data_not_found");
                    }

                }
                try {
                    previewImageLayout.setVisibility(View.VISIBLE);
                    if (ImageUtils.sizeOf(imageForPreview) > 2048) {
                        circleImageView.setImageBitmap(ImageUtils.resize(imageForPreview, imageForPreview.getHeight() / 2, imageForPreview.getWidth() / 2));
                    } else {
                        circleImageView.setImageBitmap(imageForPreview);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void setSellerFormData() {
        formSellerData.setBusinessType(busiType);
        formSellerData.setCompanyName(etProductName.getText().toString());
        formSellerData.setShopName(etProductName.getText().toString());
        formSellerData.setFirstName(etFirstName.getText().toString());
        formSellerData.setLastName(etLastName.getText().toString());
        formSellerData.setEmail(etEmail.getText().toString());
        formSellerData.setDOB(etDOB.getText() == null ? "1992-10-10" : etDOB.getText().toString());
        formSellerData.setMobile(etMobileNo.getText().toString());
        formSellerData.setPassword(etPassword.getText().toString());
        formSellerData.setConfirmPassword(etPassword.getText().toString());
        formSellerData.setClientId(App_config.getCurrentDeviceId(RegistrationActivity.this));
        formSellerData.setBusinessType(busiType == null ? "" : busiType);
        formSellerData.setCountryId(countryID == null ? "" : countryID);
        formSellerData.setStateId(stateID == null ? "" : stateID);
        formSellerData.setCityId(cityID == null ? "" : cityID);
    }


    public void getBuyerFormData() {
        formBuyerData.setCountryId(countryID == null ? "" : countryID);
        formBuyerData.setStateId(stateID == null ? "" : stateID);
        formBuyerData.setCityId(cityID == null ? "" : cityID);
        formBuyerData.setAddress(etAddress.getText().toString());
        formBuyerData.setFirstName(etFirstName.getText().toString());
        formBuyerData.setLastName(etLastName.getText().toString());
        formBuyerData.setEmail(etEmail.getText().toString());
        formBuyerData.setMobile(etMobileNo.getText().toString());
        formBuyerData.setPassword(etPassword.getText().toString());
        formBuyerData.setConfirmPassword(etPassword.getText().toString());
        formBuyerData.setClientId(App_config.getCurrentDeviceId(RegistrationActivity.this));
    }

    private String getBusiType(String busyType) {
        if (busyType.equalsIgnoreCase(spBussinessName[1]))
            return "1";
        else if (busyType.equalsIgnoreCase(spBussinessName[2]))
            return "2";
        return "";
    }


}