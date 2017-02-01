package com.example.pat.aapkatrade.Home.registration;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.IntegerRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.pat.aapkatrade.general.Validation;
import com.example.pat.aapkatrade.login.ActivityOTPVerify;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class RegistrationActivity extends AppCompatActivity {
    private static SellerRegistration formSellerData = null;
    private static BuyerRegistration formBuyerData = null;
    private boolean isAllFieldSet = false;
    private Spinner spBussinessCategory, spCountry, spState, spCity;
    private String[] spBussinessName = {"-Please Select Business Type-", "Licence", "Personal"};
    private String[] spCityName = {"-Please Select-", "Delhi", "New Delhi"};
    private EditText etProductName, etFirstName, etLastName, etDOB, etEmail, etMobileNo, etUserName, etAddress, etPassword, etReenterPassword;
    private TextView tvSave;
    private LinearLayout registrationLayout;
    private ProgressDialog dialog;
    private ArrayList<Country> countryList = new ArrayList<>();
    private ArrayList<State> stateList = new ArrayList<>();
    private ArrayList<City> cityList = new ArrayList<>();
    private LinearLayout businessDetails, uploadView;
    private static final int rcCC = 33;
    private boolean isCC = false;
    private ImageView uploadImage;
    App_sharedpreference app_sharedpreference;
    private CircleImageView circleImageView;
    private Bitmap imageForPreview;
    HashMap<String, String> webservice_header_type = new HashMap<>();
    private String busiType, countryID, stateID, cityID;
    private RelativeLayout spBussinessCategoryLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_registration);
        app_sharedpreference = new App_sharedpreference(RegistrationActivity.this);
        setuptoolbar();
        initView();
        setProgressDialogue();
        saveUserTypeInSharedPreferences();
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
                        getSellerFormData();
                        validateFields(String.valueOf(1));
                        callWebServiceForSellerRegistration();
                    }
                    /*
                    Buyer Registration
                     */
                    else if (app_sharedpreference.getsharedpref("usertype", "0").equals("2")) {

                        Log.e("reach", "reach3");
                        getBuyerFormData();
                        validateFields(String.valueOf(2));
                        callWebServiceForBuyerRegistration();
                    }
                }

            }
        });


    }

    private void setProgressDialogue() {
        dialog = new ProgressDialog(RegistrationActivity.this);
        dialog.setMessage("Please Wait\nLoading.....");
    }

    private void saveUserTypeInSharedPreferences() {
        if (app_sharedpreference != null) {
            if (app_sharedpreference.getsharedpref("usertype", "0").equals("1")) {
                etAddress.setVisibility(View.GONE);
                setUpBusinessCategory();
                Log.e("user", "user");
            }
            if (app_sharedpreference.getsharedpref("usertype", "0").equals("2")) {
                Log.e("user2", "user2");
                uploadView.setVisibility(View.GONE);
                spBussinessCategoryLayout.setVisibility(View.GONE);
                etProductName.setVisibility(View.GONE);
                etDOB.setVisibility(View.GONE);

            }
        } else {
            Log.e("user3", "user3");
        }
    }


    private void callWebServiceForSellerRegistration() {
        Ion.with(RegistrationActivity.this)
                .load("http://aapkatrade.com/slim/sellerregister")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "sellerregister")
                .setBodyParameter("business_type", getSellerFormData().getBusinessType())
                .setBodyParameter("companyname", getSellerFormData().getCompanyName())
                .setBodyParameter("name", getSellerFormData().getFirstName())
                .setBodyParameter("lastname", getSellerFormData().getLastName())
                .setBodyParameter("dob", getSellerFormData().getDOB())
                .setBodyParameter("mobile", getSellerFormData().getMobile())
                .setBodyParameter("email", getSellerFormData().getEmail())
                .setBodyParameter("password", getSellerFormData().getPassword())
                .setBodyParameter("country_id", getSellerFormData().getCountryId())
                .setBodyParameter("state_id", getSellerFormData().getStateId())
                .setBodyParameter("city_id", getSellerFormData().getCityId())
                .setBodyParameter("client_id", getSellerFormData().getClientId())
                .setBodyParameter("shopname", getSellerFormData().getCompanyName())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        Log.e("data", result.toString());
                        if (result.get("error").getAsString().equals("false")) {
                            Log.d("registration_seller", "done");
                            startActivity(new Intent(RegistrationActivity.this, ActivityOTPVerify.class));
                        }
                    }

                });
    }


    private void callWebServiceForBuyerRegistration() {
        Ion.with(RegistrationActivity.this)
                .load("http://aapkatrade.com/slim/buyerregister")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "buyerregister")
                .setBodyParameter("country_id", getBuyerFormData().getCountryId())
                .setBodyParameter("state_id", getBuyerFormData().getStateId())
                .setBodyParameter("city_id", getBuyerFormData().getCityId())
                .setBodyParameter("address", getBuyerFormData().getAddress())
                .setBodyParameter("name", getBuyerFormData().getFirstName())
                .setBodyParameter("lastname", getBuyerFormData().getLastName())
                .setBodyParameter("email", getBuyerFormData().getEmail())
                .setBodyParameter("mobile", getBuyerFormData().getMobile())
                .setBodyParameter("password", getBuyerFormData().getPassword())
                .setBodyParameter("client_id", getBuyerFormData().getClientId())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        Log.e("data", result.toString());
                        if (result.get("error").getAsString().equals("false")) {
                            Log.d("registration_buyer", "done");
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
                busiType = String.valueOf(position);
                if (spBussinessName[position].equalsIgnoreCase("Personal")) {
                    uploadView.setVisibility(View.GONE);
                    etProductName.setHint(getString(R.string.shop_name));
                } else if (spBussinessName[position].equalsIgnoreCase("Licence")) {
                    uploadView.setVisibility(View.VISIBLE);
                    etProductName.setHint(getString(R.string.company_name_heading));
                } else {
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getCountry() {
        dialog.show();
        HashMap<String, String> webservice_body_parameter = new HashMap<>();
        webservice_body_parameter.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        webservice_body_parameter.put("type", "country");

        HashMap<String, String> webservice_header_type = new HashMap<>();
        webservice_header_type.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");


        Call_webservice.getcountrystatedata(RegistrationActivity.this, "country", getResources().getString(R.string.webservice_base_url) + "/dropdown", webservice_body_parameter, webservice_header_type);

        Call_webservice.taskCompleteReminder = new TaskCompleteReminder() {
            @Override
            public void Taskcomplete(JsonObject webservice_returndata) {

                Log.e("data2", webservice_returndata.toString());

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
                    dialog.hide();
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

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }


                });
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
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        };
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

    private void initView() {
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
        // prefs = getSharedPreferences(shared_pref_name, Activity.MODE_PRIVATE);
        circleImageView = (CircleImageView) findViewById(R.id.previewImage);
        uploadImage = (ImageView) findViewById(R.id.uploadButton);
        webservice_header_type.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
    }

    private void validateFields(String userType) {
        Log.e("reach", "validateFiledsCalled");
        if (userType.equals("1")) {
            if (getSellerRegistrationInstance() != null) {
                if (Validation.isEmptyStr(getSellerRegistrationInstance().getBusinessType())
                        || getSellerRegistrationInstance().getBusinessType().equals("-Please Select Business Type-")) {
                    putError(13);
                }
                if (Validation.isEmptyStr(etProductName.getText().toString())) {
                    putError(12);
                }
                if (Validation.isEmptyStr(getSellerRegistrationInstance().getCountryId()) ||
                        Integer.parseInt(getSellerRegistrationInstance().getCountryId()) > 0) {
                    putError(6);
                }

                if (Validation.isEmptyStr(getSellerRegistrationInstance().getStateId()) ||
                        Integer.parseInt(getSellerRegistrationInstance().getStateId()) > 0) {
                    putError(7);
                }

                if (Validation.isEmptyStr(getSellerRegistrationInstance().getCityId()) ||
                        Integer.parseInt(getSellerRegistrationInstance().getCityId()) > 0) {

                    putError(8);
                }
                if (Validation.isNonEmptyStr(getSellerRegistrationInstance().getFirstName())) {
                    putError(0);
                }
                if (Validation.isNonEmptyStr(getSellerRegistrationInstance().getLastName())) {
                    putError(1);
                }
                if (Validation.isValidEmail(getSellerRegistrationInstance().getEmail())) {
                    putError(2);
                }
                if (Validation.isValidNumber(getSellerRegistrationInstance().getMobile(), Validation.getNumberPrefix(getSellerRegistrationInstance().getMobile()))) {
                    putError(3);
                }
                if (Validation.isValidPassword(getSellerRegistrationInstance().getPassword())) {
                    putError(4);
                }
                if (Validation.isPasswordMatching(getSellerRegistrationInstance().getPassword(), getSellerRegistrationInstance().getConfirmPassword())) {
                    putError(5);
                }

                if (Validation.isNonEmptyStr(etUserName.getText().toString())) {
                    putError(10);
                }
            }
            Log.d("error", "error Null");
        }
        if (userType.equals("2")) {
            Log.e("reach", "BuyerValidate Called");
            if (getBuyerRegistrationInstance() != null) {
                if (Validation.isEmptyStr(getBuyerRegistrationInstance().getCountryId()) ||
                        Integer.parseInt(getBuyerRegistrationInstance().getCountryId()) > 0) {
                    putError(6);
                }

                if (Validation.isEmptyStr(getBuyerRegistrationInstance().getStateId()) ||
                        Integer.parseInt(getBuyerRegistrationInstance().getStateId()) > 0) {
                    putError(7);
                }

                if (Validation.isEmptyStr(getBuyerRegistrationInstance().getCityId()) ||
                        Integer.parseInt(getBuyerRegistrationInstance().getCityId()) > 0) {

                    putError(8);
                }

                if (Validation.isEmptyStr(getBuyerRegistrationInstance().getAddress())) {
                    putError(9);
                }

                if (Validation.isEmptyStr(getBuyerRegistrationInstance().getFirstName())) {
                    putError(0);
                }
                if (Validation.isEmptyStr(getBuyerRegistrationInstance().getLastName())) {
                    putError(1);
                }
                if (!Validation.isValidEmail(getBuyerRegistrationInstance().getEmail())) {
                    putError(2);
                }
                if (!Validation.isValidNumber(getBuyerRegistrationInstance().getMobile(), Validation.getNumberPrefix(getBuyerRegistrationInstance().getMobile()))) {
                    putError(3);
                }
                if (Validation.isEmptyStr(getBuyerRegistrationInstance().getUserName())) {
                    putError(10);
                }
                if (!Validation.isValidPassword(getBuyerRegistrationInstance().getPassword())) {
                    putError(4);
                }
                if (!Validation.isPasswordMatching(getBuyerRegistrationInstance().getPassword(), getBuyerRegistrationInstance().getConfirmPassword())) {
                    putError(5);
                }

            }
        }


    }

    private void putError(int id) {
        switch (id) {
            case 0:
                etFirstName.setError("First Name Can't be empty");
            case 1:
                etLastName.setError("Last Name Can't be empty");
            case 2:
                etEmail.setError("Please Enter Valid Email");
            case 3:
                etMobileNo.setError("Please Enter Valid Mobile Number");
            case 4:
                etPassword.setError("Password must be greater than 6 digits");
            case 5:
                etReenterPassword.setError("Password did not matched");
            case 6:
                showmessage("Please Select Country");
            case 7:
                showmessage("Please Select State");
            case 8:
                showmessage("Please Select City");
            case 9:
                etAddress.setError("Address Can't be empty");
            case 10:
                etUserName.setError("Please Enter Valid UserName");
            case 12:
                if (etProductName.getHint().toString().equals("Personal")) {
                    etProductName.setError("Please Enter Shop Name");
                } else if (etProductName.getHint().toString().equals("Licence")) {
                    etProductName.setError("Please Enter Company Name");
                }

            case 13: showmessage("Please chose Correct Business Type");
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
            in = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
            if (rcCC == requestCode) {
                if (resultCode == Activity.RESULT_OK) {
                    isCC = true;
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

                        ParcelFileDescriptor pfd;
//                        try {
                        imageForPreview = (Bitmap) data.getExtras().get("data");


//                                imageForPreview = BitmapFactory.decodeFileDescriptor(
//                                        fileDescriptor, null, option);


//                        } catch (FileNotFoundException e) {
//                            Log.e("FileNotFoundException",e.toString());
//                        } catch (IOException e) {
//                            Log.e("IOException",e.toString());
//                        }


                        Log.e("data_not_found", "data_not_found");
                    }

                }
                try {
                    circleImageView.setImageBitmap(imageForPreview);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public SellerRegistration getSellerFormData() {
        SellerRegistration sellerRegistration = getSellerRegistrationInstance();
        setUpBusinessCategory();
        sellerRegistration.setCompanyName(etProductName.getText().toString());
        sellerRegistration.setShopName(etProductName.getText().toString());
        sellerRegistration.setFirstName(etFirstName.getText().toString());
        sellerRegistration.setLastName(etLastName.getText().toString());
        sellerRegistration.setEmail(etEmail.getText().toString());
        sellerRegistration.setDOB(etDOB.getText() == null ? "1992-10-10" : etDOB.getText().toString());
        sellerRegistration.setMobile(etMobileNo.getText().toString());
        sellerRegistration.setPassword(etPassword.getText().toString());
        sellerRegistration.setConfirmPassword(etPassword.getText().toString());
        sellerRegistration.setClientId(App_config.getCurrentDeviceId(RegistrationActivity.this));
        sellerRegistration.setBusinessType(busiType == null ? "" : busiType);
        sellerRegistration.setCountryId(countryID == null ? "" : countryID);
        sellerRegistration.setStateId(stateID == null ? "" : stateID);
        sellerRegistration.setCityId(cityID == null ? "" : cityID);
        return sellerRegistration;
    }


    public BuyerRegistration getBuyerFormData() {
        BuyerRegistration buyerRegistration = getBuyerRegistrationInstance();
        buyerRegistration.setCountryId(countryID == null ? "" : countryID);
        buyerRegistration.setStateId(stateID == null ? "" : stateID);
        buyerRegistration.setCityId(cityID == null ? "" : cityID);
        buyerRegistration.setAddress(etAddress.getText().toString());
        buyerRegistration.setFirstName(etFirstName.getText().toString());
        buyerRegistration.setLastName(etLastName.getText().toString());
        buyerRegistration.setEmail(etEmail.getText().toString());
        buyerRegistration.setMobile(etMobileNo.getText().toString());
        buyerRegistration.setPassword(etPassword.getText().toString());
        buyerRegistration.setConfirmPassword(etPassword.getText().toString());
        buyerRegistration.setClientId(App_config.getCurrentDeviceId(RegistrationActivity.this));
        return buyerRegistration;
    }


    public static SellerRegistration getSellerRegistrationInstance() {
        if (formSellerData == null) {
            return new SellerRegistration();
        }
        return formSellerData;
    }

    public static BuyerRegistration getBuyerRegistrationInstance() {
        if (formBuyerData == null) {
            return new BuyerRegistration();
        }
        return formBuyerData;
    }
}