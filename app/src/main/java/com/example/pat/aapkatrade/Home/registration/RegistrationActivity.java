package com.example.pat.aapkatrade.Home.registration;

import android.app.Activity;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.pat.aapkatrade.general.ConnectivityNotFound;
import com.example.pat.aapkatrade.general.ConnetivityCheck;
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
    private static SellerRegistration formData = null;
    private boolean isAllFieldSet = false;
    private Spinner spBussinessCategory, spCountry, spState, spCity;
    private String[] spBussinessName = {"-Please Select-", "Licence", "Personal"};
    private String[] spCityName = {"-Please Select-", "Delhi", "New Delhi"};
    private EditText etProductName, etFirstName, etLastName, etDOB, etEmail, etMobileNo, etUserName, etPassword, etReenterPassword;
    private TextView tvSave;
    private CoordinatorLayout cl;
    private ProgressDialog dialog;
    private ArrayList<Country> countryList = new ArrayList<>();
    private ArrayList<State> stateList = new ArrayList<>();
    private ArrayList<City> cityList = new ArrayList<>();
//    private static String shared_pref_name = "aapkatrade";
//    private SharedPreferences prefs;
    private LinearLayout businessDetails, uploadView;
    private static final int rcCC = 33;
    private boolean isCC = false;
    private ImageView uploadImage;
    App_sharedpreference app_sharedpreference;
    private CircleImageView circleImageView;
    private Bitmap imageForPreview;
    HashMap<String, String> webservice_header_type = new HashMap<>();
    private String busiType, countryID, stateID, cityID;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        app_sharedpreference=new App_sharedpreference(RegistrationActivity.this);
        setuptoolbar();
        initView();
        webservice_header_type.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        saveUserTypeInSharedPreferences();
        setUpBusinessCategory();
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picPhoto();
            }
        });
        saveProfile();

    }

    private void saveUserTypeInSharedPreferences() {
        if (app_sharedpreference != null) {
            if (app_sharedpreference.getsharedpref("usertype", "0") .equals("1") ) {
                getCountry();
                Log.e("user","user");
            }
            if (app_sharedpreference.getsharedpref("usertype", "0") .equals("2")) {
                Log.e("user2","user2");
               // dialog.hide();
                businessDetails.setVisibility(View.GONE);
            }
        }
        else{
            Log.e("user3","user3");
        }
    }

    private void saveProfile() {
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getFormData();
               // validateFields();
                Toast.makeText(RegistrationActivity.this, "fsg fsvfvshgvfahsgvf"+getSellerRegistrationInstance().toString(), Toast.LENGTH_SHORT).show();
//                Log.d("checkData", getSellerRegistrationInstance().toString());
                //if(isAllFieldSet)
                callWebServiceForRegistration();
            }
        });
    }

    private void callWebServiceForRegistration() {




if(ConnetivityCheck.isNetworkAvailable(RegistrationActivity.this))

{

    Ion.with(RegistrationActivity.this)
            .load("http://aapkatrade.com/slim/sellerregister")
            .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
            .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
            .setBodyParameter("type", "sellerregister")
            .setBodyParameter("business_type", getFormData().getBusinessType())
            .setBodyParameter("companyname", getFormData().getCompanyName())
            .setBodyParameter("name", getFormData().getFirstName())
            .setBodyParameter("lastname", getFormData().getLastName())
            .setBodyParameter("dob", getFormData().getDOB())
            .setBodyParameter("mobile", getFormData().getMobile())
            .setBodyParameter("email", getFormData().getEmail())
            .setBodyParameter("password", getFormData().getPassword())
            .setBodyParameter("country_id", getFormData().getCountryId())
            .setBodyParameter("state_id", getFormData().getStateId())
            .setBodyParameter("city_id", getFormData().getCityId())
            .setBodyParameter("client_id", getFormData().getClientId())
            .setBodyParameter("shopname", getFormData().getCompanyName())
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
        else{



    Log.e("network not found","network not found");
}
    }

    private void setUpBusinessCategory() {
        spBussinessCategory.setAdapter(new SpBussinessAdapter(getApplicationContext(), spBussinessName));
        spBussinessCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                getSellerRegistrationInstance().setBusinessType(spBussinessName[position]);
                busiType = String.valueOf(position);
                if (spBussinessName[position].equalsIgnoreCase("Personal")) {
                    uploadView.setVisibility(View.GONE);
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
        if(ConnetivityCheck.isNetworkAvailable(RegistrationActivity.this))

        {
            // dialog.show();
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
                        Country countryEntity_init = new Country("-1", "Select country");
                        countryList.add(countryEntity_init);
                        for (int i = 0; i < jsonResultArray.size(); i++) {

                            JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);

                            Country countryEntity = new Country(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString());
                            countryList.add(countryEntity);
                        }
                        //        dialog.hide();
                        SpCountrysAdapter spCountrysAdapter = new SpCountrysAdapter(RegistrationActivity.this, countryList);
                        spCountry.setAdapter(spCountrysAdapter);
//
                        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                Log.d("datacountry", countryList.get(position).countryId);
                                countryID = countryList.get(position).countryId;
//                                    Log.d("datacountryobj", getSellerRegistrationInstance().getCountryId());
//                                    getSellerRegistrationInstance().setCountryId(countryList.get(position).countryId);
                                stateList = new ArrayList<State>();
                                if (position > 0) {
                                    getState(countryList.get(position).countryId);
                                }


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }


                        });
                    }

                    else {
                        Log.e("webservice_null", "null");
                    }

                }
            };
        }
        else{
            Intent network_prob=new Intent(RegistrationActivity.this, ConnectivityNotFound.class);
            startActivity(network_prob);

            Log.e(" notwork not found","network not found");


        }
    }

    public void getState(String countryId) {


        HashMap<String, String> webservice_body_parameter = new HashMap<>();
        webservice_body_parameter.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        webservice_body_parameter.put("type", "state");
        webservice_body_parameter.put("id", countryId);




        Call_webservice.getcountrystatedata(RegistrationActivity.this, "state", getResources().getString(R.string.webservice_base_url) + "/dropdown", webservice_body_parameter, webservice_header_type);

        Call_webservice.taskCompleteReminder = new TaskCompleteReminder() {
            @Override
            public void Taskcomplete(JsonObject state_data_webservice) {

                JsonObject jsonObject = state_data_webservice.getAsJsonObject();
                JsonArray jsonResultArray = jsonObject.getAsJsonArray("result");
                stateList.clear();
                State stateEntity_init = new State("-1", "Select state");
                stateList.add(stateEntity_init);


                for (int i = 0; i < jsonResultArray.size(); i++) {
                    JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                    State stateEntity = new State(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString());
                    stateList.add(stateEntity);
//                                            Log.d("data", stateEntity.stateName);
                }
//                dialog.hide();
                SpStateAdapter spStateAdapter = new SpStateAdapter(RegistrationActivity.this, stateList);
                spState.setAdapter(spStateAdapter);

                spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {
//                        getSellerRegistrationInstance().setStateId(stateList.get(position).stateId);
                        stateID = stateList.get(position).stateId;
                        Log.d("datastate", stateList.get(position).stateId);
//                        Log.d("datastateobj", getSellerRegistrationInstance().getStateId());
                        cityList = new ArrayList<City>();
                        if(position>0) {
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
        Log.d("data", stateId);
        HashMap<String, String> webservice_body_parameter = new HashMap<>();
        webservice_body_parameter.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        webservice_body_parameter.put("type", "city");
        webservice_body_parameter.put("id", stateId);




        Call_webservice.getcountrystatedata(RegistrationActivity.this, "city", getResources().getString(R.string.webservice_base_url) + "/dropdown", webservice_body_parameter, webservice_header_type);

        Call_webservice.taskCompleteReminder = new TaskCompleteReminder() {
            @Override
            public void Taskcomplete(JsonObject city_data_webservice) {


                Log.d("data", city_data_webservice.toString());
                JsonObject jsonObject = city_data_webservice.getAsJsonObject();
                JsonArray jsonResultArray = jsonObject.getAsJsonArray("result");
                for (int i = 0; i < jsonResultArray.size(); i++) {
                    JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                    City cityEntity = new City(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString());
                    cityList.add(cityEntity);
                }
                // dialog.hide();
                SpCityAdapter spCityAdapter = new SpCityAdapter(RegistrationActivity.this, cityList);
                spCity.setAdapter(spCityAdapter);

                spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        getSellerRegistrationInstance().setCityId(cityList.get(position).cityId);
                        cityID = cityList.get(position).cityId;
                        Log.d("datacity", cityList.get(position).cityId);
//                        Log.d("datacityobj", getSellerRegistrationInstance().getCityId());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        };







        //dialog.show();

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
        cl = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
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
        businessDetails = (LinearLayout) findViewById(R.id.businessDetails);
        etReenterPassword = (EditText) findViewById(R.id.etReenterPassword);
        uploadView = (LinearLayout) findViewById(R.id.uploadView);
        //prefs = getSharedPreferences(shared_pref_name, Activity.MODE_PRIVATE);
        circleImageView = (CircleImageView) findViewById(R.id.previewImage);
        uploadImage = (ImageView) findViewById(R.id.uploadButton);


    }

    private void validateFields() {
        if(getSellerRegistrationInstance()!=null) {
            if (Validation.isNonEmptyStr(getSellerRegistrationInstance().getFirstName())) {
                if (Validation.isNonEmptyStr(getSellerRegistrationInstance().getLastName())) {
                    if (Validation.isValidEmail(getSellerRegistrationInstance().getEmail())) {
                        if (Validation.isValidNumber(getSellerRegistrationInstance().getMobile(), Validation.getNumberPrefix(getSellerRegistrationInstance().getMobile()))) {
                            if (Validation.isValidPassword(getSellerRegistrationInstance().getPassword())) {
                                if (Validation.isPasswordMatching(getSellerRegistrationInstance().getPassword(), getSellerRegistrationInstance().getConfirmPassword())) {
                                    isAllFieldSet = true;
                                } else {
                                    putError(5);
                                }
                            } else {
                                putError(4);
                            }
                        } else {
                            putError(3);
                        }
                    } else {
                        putError(2);
                    }
                } else {
                    putError(1);
                }
            } else {
                putError(0);
            }
        } Log.d("error", "error Null");
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
                    if(data.getData()!=null) {

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
                           Log.e("FileNotFoundException",e.toString());
                        } catch (IOException e) {
                            Log.e("IOException",e.toString());
                        }
                    }

                    else{

                        ParcelFileDescriptor pfd;
//                        try {
   imageForPreview = (Bitmap)data.getExtras().get("data");


//                                imageForPreview = BitmapFactory.decodeFileDescriptor(
//                                        fileDescriptor, null, option);




//                        } catch (FileNotFoundException e) {
//                            Log.e("FileNotFoundException",e.toString());
//                        } catch (IOException e) {
//                            Log.e("IOException",e.toString());
//                        }






                        Log.e("data_not_found","data_not_found");
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


    public SellerRegistration getFormData() {
        SellerRegistration sellerRegistration = getSellerRegistrationInstance();
        setUpBusinessCategory();
        sellerRegistration.setCompanyName(etProductName.getText().toString());
        sellerRegistration.setShopName(etProductName.getText().toString());
        sellerRegistration.setFirstName(etFirstName.getText().toString());
        sellerRegistration.setLastName(etLastName.getText().toString());
        sellerRegistration.setEmail(etEmail.getText().toString());
        sellerRegistration.setDOB(etDOB.getText()==null?"1992-10-10":etDOB.getText().toString());
        sellerRegistration.setMobile(etMobileNo.getText().toString());
        sellerRegistration.setPassword(etPassword.getText().toString());
        sellerRegistration.setConfirmPassword(etPassword.getText().toString());
        sellerRegistration.setClientId(App_config.getCurrentDeviceId(RegistrationActivity.this));
        sellerRegistration.setBusinessType(busiType==null?"":busiType);
        sellerRegistration.setCountryId(countryID==null?"":countryID);
        sellerRegistration.setStateId(stateID==null?"":stateID);
        sellerRegistration.setCityId(cityID==null?"":cityID);
        return sellerRegistration;
    }

    public static SellerRegistration getSellerRegistrationInstance() {
        if(formData == null){
            Log.d("datacall", "^^^^^^^^^^");
            return new SellerRegistration();
        }
        return formData;
    }

    private String[] getDOBArray(String s){
        String dob[] = new String[3];
        if(Validation.isNonEmptyStr(s)){
            dob = s.split("-");
            if(dob.length == 3){
                return dob;
            }
        }
        return dob = new String[]{"15","11","1994"};
    }
}
