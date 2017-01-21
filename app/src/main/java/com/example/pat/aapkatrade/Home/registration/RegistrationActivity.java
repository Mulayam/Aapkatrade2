package com.example.pat.aapkatrade.Home.registration;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.pat.aapkatrade.Home.registration.entity.City;
import com.example.pat.aapkatrade.Home.registration.entity.Country;
import com.example.pat.aapkatrade.Home.registration.entity.State;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpBussinessAdapter;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpCityAdapter;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpCountrysAdapter;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpStateAdapter;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Validation;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


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
    private static String shared_pref_name = "aapkatrade";
    private SharedPreferences prefs;
    private LinearLayout businessDetails, uploadView;
    private static final int rcCC = 33;
    private boolean isCC = false;
    private ImageView uploadImage;
    private CircleImageView circleImageView;
    private Bitmap imageForPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setuptoolbar();
        initView();
        dialog = ProgressDialog.show(RegistrationActivity.this, "", "Loading. Please wait...", true);
        saveUserTypeInSharedPreferences();
        setUpBusinessCategory();
        saveProfile();
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picPhoto();
            }
        });
    }

    private void saveUserTypeInSharedPreferences() {
        if (prefs != null) {
            if (prefs.getInt("user", 0) == 1) {
                getCountry();
            }
            if (prefs.getInt("user", 0) == 2) {
                dialog.hide();
                businessDetails.setVisibility(View.GONE);
            }
        }
    }

    private void saveProfile() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateFields(etFirstName.getText().toString(), etLastName.getText().toString(), etEmail.getText().toString(),
                        etMobileNo.getText().toString(), etPassword.getText().toString(), etReenterPassword.getText().toString()
                );
            }
        });
    }

    private void setUpBusinessCategory() {
        spBussinessCategory.setAdapter(new SpBussinessAdapter(getApplicationContext(), spBussinessName));
        spBussinessCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (spBussinessName[position].equalsIgnoreCase("Personal")) {
                    uploadView.setVisibility(View.GONE);
                } else if (spBussinessName[position].equalsIgnoreCase("Business")) {
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

    private void initView() {
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
        uploadView = (LinearLayout) findViewById(R.id.uploadView);
        prefs = getSharedPreferences(shared_pref_name, Activity.MODE_PRIVATE);
        circleImageView = (CircleImageView) findViewById(R.id.previewImage);
        uploadImage = (ImageView) findViewById(R.id.uploadButton);
    }

    private void validateFields(String fName, String lName, String email, String mobileNumber, String password, String confirmPassword) {
        if (Validation.isNonEmptyStr(fName)) {
            if (Validation.isNonEmptyStr(lName)) {
                if (Validation.isValidEmail(email)) {
                    if (Validation.isValidNumber(mobileNumber, Validation.getNumberPrefix(mobileNumber))) {
                        if (Validation.isValidPassword(password)) {
                            if (Validation.isPasswordMatching(password, confirmPassword)) {

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

                    ParcelFileDescriptor pfd;
                    try {
                        pfd = getContentResolver()
                                .openFileDescriptor(data.getData(), "r");
                        if(pfd!=null) {
                            FileDescriptor fileDescriptor = pfd
                                    .getFileDescriptor();

                            imageForPreview = BitmapFactory.decodeFileDescriptor(
                                    fileDescriptor, null, option);
                        }
                        pfd.close();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
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


}
