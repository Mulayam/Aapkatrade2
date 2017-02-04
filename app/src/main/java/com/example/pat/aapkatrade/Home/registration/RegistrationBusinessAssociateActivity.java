package com.example.pat.aapkatrade.Home.registration;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.registration.entity.BusinessAssociateRegistration;
import com.example.pat.aapkatrade.Home.registration.entity.City;
import com.example.pat.aapkatrade.Home.registration.entity.State;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpCityAdapter;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpStateAdapter;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Call_webservice;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Utils.ImageUtils;
import com.example.pat.aapkatrade.general.TaskCompleteReminder;
import com.example.pat.aapkatrade.general.Validation;
import com.example.pat.aapkatrade.user_dashboard.add_product.CustomSpinnerAdapter;
import com.example.pat.aapkatrade.user_dashboard.add_product.SpinnerAdapter;
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

public class RegistrationBusinessAssociateActivity extends AppCompatActivity {
    private Context context;
    private TextView uploadMsg, tvSave;
    private static BusinessAssociateRegistration formBusinessData = new BusinessAssociateRegistration();
    private TextView step1HeaderCircle, step2HeaderCircle, step3HeaderCircle;
    private TextView step1HeaderText, step2HeaderText, step3HeaderText;
    private ImageView uploadImage, openCalander, cancelImage;
    private HashMap<String, String> webservice_header_type = new HashMap<>();
    private EditText et_email, et_password, et_confirm_password, et_first_name, et_last_name, et_father_name, et_mobile, et_account_no, et_branch_code, et_branch_name, et_ifsc_code, et_micr_code, et_account_holder_name, et_registered_mobile_with_bank, etDOB, et_address, et_pincode;
    private Spinner spState, spCity, spQualification, spTotalExp, spRelExp, spSelectBank;
    private CheckBox agreement_check;
    private Bitmap imageForPreview;
    private CircleImageView circleImageView;
    private static final int reqCode = 33;
    private boolean isReqCode = false;
    private ArrayList<State> stateList = new ArrayList<>();
    private ArrayList<City> cityList = new ArrayList<>();
    private String stateID, cityID, qualification, totalExperience, relaventExperience, bankName;
    private RelativeLayout previewImageLayout;
    private CardView step1aLayout, step1bLayout, step1cLayout, step2Layout, step3Layout;
    private LinearLayout registrationLayout;
    private int step1FieldsSet = 0, step2FieldsSet = 0,step3FieldsSet = 0;
    private int stepNumber = 1;
    private ArrayList<String> qualificationList = new ArrayList<>(), totalExpList = new ArrayList<>(), relaventExpList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_business_associate);
        initView();
        setStepHeader(1);
        getState();
        uploadImageCall();
        saveAndContinue();
        openCalender();
    }

    private void callWebServiceForRegistration() {
        Ion.with(context)
                .load("http://aapkatrade.com/slim/businessregister")
                .setHeader("authorization", webservice_header_type.get("authorization"))
                .setBodyParameter("authorization", webservice_header_type.get("authorization"))
                .setBodyParameter("type", "3")
                .setBodyParameter("email", formBusinessData.getEmail())
                .setBodyParameter("password", formBusinessData.getPassword())
                .setBodyParameter("confirm_password", formBusinessData.getConfirmPassword())
                .setBodyParameter("first_name", formBusinessData.getFirstName())
                .setBodyParameter("last_name", formBusinessData.getLastName())
                .setBodyParameter("father_name", formBusinessData.getFatherName())
                .setBodyParameter("mobile", formBusinessData.getMobile_no())
                .setBodyParameter("dob", formBusinessData.getDob())
                .setBodyParameter("address", formBusinessData.getAddress())
                .setBodyParameter("stateId", formBusinessData.getStateID())
                .setBodyParameter("cityId", formBusinessData.getCityID())
                .setBodyParameter("pincode", formBusinessData.getPinCode())
                .setBodyParameter("isAgree", String.valueOf(formBusinessData.isAgreementAccepted()))
                .setBodyParameter("qualification", formBusinessData.getQualification())
                .setBodyParameter("total_experience", formBusinessData.getTotalExperience())
                .setBodyParameter("relavent_experience", formBusinessData.getRelaventExperience())
                .setBodyParameter("bank_name", formBusinessData.getBankName())
                .setBodyParameter("account_number", formBusinessData.getEmail())
                .setBodyParameter("branch_code", formBusinessData.getEmail())
                .setBodyParameter("branch_name", formBusinessData.getEmail())
                .setBodyParameter("ifsc_code", formBusinessData.getEmail())
                .setBodyParameter("micr_code", formBusinessData.getEmail())
                .setBodyParameter("account_holder_name", formBusinessData.getEmail())
                .setBodyParameter("registered_mobile_number", formBusinessData.getEmail())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Log.d("result", result.getAsString());
                    }

                });
    }

    private void uploadImageCall() {
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picPhoto();
            }
        });

        cancelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previewImageLayout.setVisibility(View.GONE);
            }
        });

    }

    private void openCalender() {
        openCalander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar newCalendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegistrationBusinessAssociateActivity.this, R.style.myCalTheme, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        showDate(year, month + 1, day);
                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
    }

    private void saveAndContinue() {
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("hi", "stepnumber"+stepNumber);
                setBusinessFormData(stepNumber);
                validateFields(stepNumber);
                if(step1FieldsSet == 0){
                    setBusinessFormData(1);
                } else if(step2FieldsSet == 0){
                    setBusinessFormData(2);
                } else if(step3FieldsSet == 0){
                    setBusinessFormData(3);
                }
            }
        });
    }

    private void initView() {
        context = RegistrationBusinessAssociateActivity.this;
        webservice_header_type.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        setuptoolbar();
        registrationLayout = (LinearLayout) findViewById(R.id.register_busi_assoc_layout);
        uploadMsg = (TextView) findViewById(R.id.uploadMsg);
        uploadMsg.setText("Attach Your Photo");
        step1HeaderCircle = (TextView) findViewById(R.id.step1Circle);
        step2HeaderCircle = (TextView) findViewById(R.id.step2Circle);
        step3HeaderCircle = (TextView) findViewById(R.id.step3Circle);
        step1HeaderText = (TextView) findViewById(R.id.step1HeaderText);
        step2HeaderText = (TextView) findViewById(R.id.step2HeaderText);
        step3HeaderText = (TextView) findViewById(R.id.step3HeaderText);
        uploadImage = (ImageView) findViewById(R.id.uploadButton);
        openCalander = (ImageView) findViewById(R.id.openCalander);
        circleImageView = (CircleImageView) findViewById(R.id.previewImage);
        previewImageLayout = (RelativeLayout) findViewById(R.id.previewImageLayout);
        cancelImage = (ImageView) findViewById(R.id.cancelImage);
        etDOB = (EditText) findViewById(R.id.et_dob);
        tvSave = (TextView) findViewById(R.id.tvSave);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        et_confirm_password = (EditText) findViewById(R.id.et_confirm_password);
        et_first_name = (EditText) findViewById(R.id.et_first_name);
        et_last_name = (EditText) findViewById(R.id.et_last_name);
        et_father_name = (EditText) findViewById(R.id.et_father_name);
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        et_account_no = (EditText) findViewById(R.id.et_account_no);
        et_branch_code = (EditText) findViewById(R.id.et_branch_code);
        et_branch_name = (EditText) findViewById(R.id.et_branch_name);
        et_ifsc_code = (EditText) findViewById(R.id.et_ifsc_code);
        et_micr_code = (EditText) findViewById(R.id.et_micr_code);
        et_account_holder_name = (EditText) findViewById(R.id.et_account_holder_name);
        et_registered_mobile_with_bank = (EditText) findViewById(R.id.et_registered_mobile_with_bank);
        et_address = (EditText) findViewById(R.id.et_address);
        et_pincode = (EditText) findViewById(R.id.et_pincode);
        spState = (Spinner) findViewById(R.id.spState);
        spCity = (Spinner) findViewById(R.id.spcity);
        spQualification = (Spinner) findViewById(R.id.spQualification);
        spTotalExp = (Spinner) findViewById(R.id.spTotalExp);
        spRelExp = (Spinner) findViewById(R.id.spRelExp);
        spSelectBank = (Spinner) findViewById(R.id.spSelectBank);
        agreement_check = (CheckBox) findViewById(R.id.agreement_check);
        step1aLayout = (CardView) findViewById(R.id.step1aLayout);
        step1bLayout = (CardView) findViewById(R.id.step1bLayout);
        step1cLayout = (CardView) findViewById(R.id.step1cLayout);
        step2Layout = (CardView) findViewById(R.id.step2Layout);
        step3Layout = (CardView) findViewById(R.id.step3Layout);

        State stateEntity_init = new State("-1", "Please Select State");
        stateList.add(stateEntity_init);
        SpStateAdapter spStateAdapter = new SpStateAdapter(context, stateList);
        spState.setAdapter(spStateAdapter);

        City cityEntity_init = new City("-1", "Please Select City");
        cityList.add(cityEntity_init);
        SpCityAdapter spCityAdapter = new SpCityAdapter(context, cityList);
        spCity.setAdapter(spCityAdapter);







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

    private void setuptoolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }

    private void setStepHeader(int stepNo) {
        if (stepNo == 1) {
            step1HeaderCircle.setTextColor(ContextCompat.getColor(context, R.color.orange));
            step1HeaderText.setTextColor(ContextCompat.getColor(context, R.color.orange));
            step2HeaderCircle.setBackground(ContextCompat.getDrawable(context, R.drawable.green_circle_border_solid));
            step2HeaderCircle.setTextColor(ContextCompat.getColor(context, R.color.white));
            step2HeaderText.setTextColor(ContextCompat.getColor(context, R.color.green));
            step3HeaderCircle.setBackground(ContextCompat.getDrawable(context, R.drawable.green_circle_border_solid));
            step3HeaderCircle.setTextColor(ContextCompat.getColor(context, R.color.white));
            step3HeaderText.setTextColor(ContextCompat.getColor(context, R.color.green));
            step1aLayout.setVisibility(View.VISIBLE);
            step1bLayout.setVisibility(View.VISIBLE);
            step1cLayout.setVisibility(View.VISIBLE);
            step2Layout.setVisibility(View.GONE);
            step3Layout.setVisibility(View.GONE);
        } else if (stepNo == 2) {
            step2HeaderCircle.setTextColor(ContextCompat.getColor(context, R.color.orange));
            step2HeaderText.setTextColor(ContextCompat.getColor(context, R.color.orange));
            step1HeaderCircle.setBackground(ContextCompat.getDrawable(context, R.drawable.green_circle_border_solid));
            step1HeaderCircle.setTextColor(ContextCompat.getColor(context, R.color.white));
            step1HeaderText.setTextColor(ContextCompat.getColor(context, R.color.green));
            step3HeaderCircle.setBackground(ContextCompat.getDrawable(context, R.drawable.green_circle_border_solid));
            step3HeaderCircle.setTextColor(ContextCompat.getColor(context, R.color.white));
            step3HeaderText.setTextColor(ContextCompat.getColor(context, R.color.green));
            step1aLayout.setVisibility(View.GONE);
            step1bLayout.setVisibility(View.GONE);
            step1cLayout.setVisibility(View.GONE);
            step2Layout.setVisibility(View.VISIBLE);
            step3Layout.setVisibility(View.GONE);

        } else if (stepNo == 3) {
            step3HeaderCircle.setTextColor(ContextCompat.getColor(context, R.color.orange));
            step3HeaderText.setTextColor(ContextCompat.getColor(context, R.color.orange));
            step2HeaderCircle.setBackground(ContextCompat.getDrawable(context, R.drawable.green_circle_border_solid));
            step2HeaderCircle.setTextColor(ContextCompat.getColor(context, R.color.white));
            step2HeaderText.setTextColor(ContextCompat.getColor(context, R.color.green));
            step1HeaderCircle.setBackground(ContextCompat.getDrawable(context, R.drawable.green_circle_border_solid));
            step1HeaderCircle.setTextColor(ContextCompat.getColor(context, R.color.white));
            step1HeaderText.setTextColor(ContextCompat.getColor(context, R.color.green));
            step1aLayout.setVisibility(View.GONE);
            step1bLayout.setVisibility(View.GONE);
            step1cLayout.setVisibility(View.GONE);
            step2Layout.setVisibility(View.GONE);
            step3Layout.setVisibility(View.VISIBLE);

        }
    }

    private void showDate(int year, int month, int day) {
        etDOB.setText(new StringBuilder().append(year).append("-").append(month).append("-").append(day));
    }


    public void getState() {


        HashMap<String, String> webservice_body_parameter = new HashMap<>();
        webservice_body_parameter.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        webservice_body_parameter.put("type", "state");
        webservice_body_parameter.put("id", "101");//country id fixed 101 for India

        Call_webservice.getcountrystatedata(context, "state", getResources().getString(R.string.webservice_base_url) + "/dropdown", webservice_body_parameter, webservice_header_type);

        Call_webservice.taskCompleteReminder = new TaskCompleteReminder() {
            @Override
            public void Taskcomplete(JsonObject state_data_webservice) {
                Log.e("Taskcomplete", "TaskcompleteError" + state_data_webservice.toString());
                JsonObject jsonObject = state_data_webservice.getAsJsonObject();
                JsonArray jsonResultArray = jsonObject.getAsJsonArray("result");
                stateList.clear();
//                State stateEntity_init = new State("-1", "-Pleas Select State-");
//                stateList.add(stateEntity_init);

                for (int i = 0; i < jsonResultArray.size(); i++) {
                    JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                    State stateEntity = new State(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString());
                    stateList.add(stateEntity);
                }
                SpStateAdapter spStateAdapter = new SpStateAdapter(context, stateList);
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

    public void getCity(String stateId) {

        HashMap<String, String> webservice_body_parameter = new HashMap<>();
        webservice_body_parameter.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        webservice_body_parameter.put("type", "city");
        webservice_body_parameter.put("id", stateId);

        Call_webservice.getcountrystatedata(context, "city", getResources().getString(R.string.webservice_base_url) + "/dropdown", webservice_body_parameter, webservice_header_type);

        Call_webservice.taskCompleteReminder = new TaskCompleteReminder() {
            @Override
            public void Taskcomplete(JsonObject city_data_webservice) {

                JsonObject jsonObject = city_data_webservice.getAsJsonObject();
                JsonArray jsonResultArray = jsonObject.getAsJsonArray("result");

//                City cityEntity_init = new City("-1", "-Please Select City-");
//                cityList.add(cityEntity_init);

                for (int i = 0; i < jsonResultArray.size(); i++) {
                    JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                    City cityEntity = new City(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString());
                    cityList.add(cityEntity);
                }

                SpCityAdapter spCityAdapter = new SpCityAdapter(context, cityList);
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


    public void setBusinessFormData(int stepNo) {

        if (stepNo == 1) {
            formBusinessData.setEmail(et_email.getText() == null ? "" : et_email.getText().toString());
            formBusinessData.setPassword(et_password.getText() == null ? "" : et_password.getText().toString());
            formBusinessData.setConfirmPassword(et_confirm_password.getText() == null ? "" : et_confirm_password.getText().toString());
            formBusinessData.setFirstName(et_first_name.getText() == null ? "" : et_first_name.getText().toString());
            formBusinessData.setLastName(et_last_name.getText() == null ? "" : et_last_name.getText().toString());
            formBusinessData.setFatherName(et_father_name.getText() == null ? "" : et_father_name.getText().toString());
            formBusinessData.setMobile_no(et_mobile.getText() == null ? "" : et_mobile.getText().toString());
            formBusinessData.setDob(etDOB.getText() == null ? "" : etDOB.getText().toString());
            formBusinessData.setAddress(et_address.getText() == null ? "" : et_address.getText().toString());
            formBusinessData.setStateID(stateID);
            formBusinessData.setCityID(cityID);
            formBusinessData.setPinCode(et_pincode.getText() == null ? "" : et_pincode.getText().toString());
            formBusinessData.setAgreementAccepted(agreement_check.isChecked());
        } else if (stepNo == 2) {

            formBusinessData.setQualification(qualification == null ? "" : qualification);
            formBusinessData.setTotalExperience(totalExperience == null ? "" : totalExperience);
            formBusinessData.setRelaventExperience(relaventExperience == null ? "" : relaventExperience);
        } else if (stepNo == 3) {
            formBusinessData.setBankName(bankName == null ? "" : bankName);
            formBusinessData.setAccountNumber(et_account_no == null ? "" : et_account_no.getText().toString());
            formBusinessData.setBranchCode(et_branch_code == null ? "" : et_branch_code.getText().toString());
            formBusinessData.setBranchName(et_branch_name == null ? "" : et_branch_name.getText().toString());
            formBusinessData.setIfscCode(et_ifsc_code == null ? "" : et_ifsc_code.getText().toString());
            formBusinessData.setMicrCode(et_micr_code == null ? "" : et_micr_code.getText().toString());
            formBusinessData.setAccountHolderName(et_account_holder_name == null ? "" : et_account_holder_name.getText().toString());
            formBusinessData.setRegisteredMobileWithBank(et_registered_mobile_with_bank == null ? "" : et_registered_mobile_with_bank.getText().toString());
        }
    }


    private void validateFields(int stepNo) {
        if (formBusinessData != null) {
            if (stepNo == 1) {
                step1FieldsSet = 0;
                if (Validation.isEmptyStr(formBusinessData.getEmail())) {
                    putError(2);
                    step1FieldsSet++;
                } else if (!Validation.isValidPassword(formBusinessData.getPassword())) {
                    putError(4);
                    step1FieldsSet++;
                } else if (!Validation.isPasswordMatching(formBusinessData.getPassword(), formBusinessData.getConfirmPassword())) {
                    putError(5);
                    step1FieldsSet++;
                } else if (Validation.isEmptyStr(formBusinessData.getFirstName())) {
                    putError(0);
                    step1FieldsSet++;
                } else if (Validation.isEmptyStr(formBusinessData.getLastName())) {
                    putError(1);
                    step1FieldsSet++;
                } else if (Validation.isEmptyStr(formBusinessData.getFatherName())) {
                    putError(10);
                    step1FieldsSet++;
                } else if (Validation.isEmptyStr(formBusinessData.getMobile_no())) {
                    putError(3);
                    step1FieldsSet++;
                } else if (!Validation.isValidDOB(formBusinessData.getDob())) {
                    putError(6);
                    step1FieldsSet++;
                } else if (Validation.isEmptyStr(formBusinessData.getAddress())) {
                    putError(9);
                    step1FieldsSet++;
                } else if (Validation.isEmptyStr(formBusinessData.getLastName())) {
                    putError(1);
                    step1FieldsSet++;
                } else if(!(Validation.isNonEmptyStr(formBusinessData.getStateID()) &&
                        Integer.parseInt(formBusinessData.getStateID()) > 0)) {
                    AndroidUtils.showSnackBar(registrationLayout, "Please Select State");
                    step1FieldsSet++;
                } else if(!(Validation.isNonEmptyStr(formBusinessData.getCityID()) &&
                        Integer.parseInt(formBusinessData.getCityID()) > 0)) {
                    AndroidUtils.showSnackBar(registrationLayout, "Please Select City");
                    step1FieldsSet++;
                } else if(!Validation.isPincode(formBusinessData.getPinCode())){
                    putError(11);
                    step1FieldsSet++;
                } else if(!formBusinessData.isAgreementAccepted()){
                    putError(7);
                    step1FieldsSet++;
                }
                if(step1FieldsSet == 0){
                    stepNo = 2;
                }
            } else if (stepNo == 2) {
                step1FieldsSet = 0;
                if(!(Validation.isNonEmptyStr(formBusinessData.getQualification()) &&
                        Integer.parseInt(formBusinessData.getQualification()) > 0)) {
                    AndroidUtils.showSnackBar(registrationLayout, "Please Select Qualification");
                    step2FieldsSet++;
                } else  if(!(Validation.isNonEmptyStr(formBusinessData.getTotalExperience()) &&
                        Integer.parseInt(formBusinessData.getTotalExperience()) >= 0)) {
                    AndroidUtils.showSnackBar(registrationLayout, "Please Select Total Experience");
                    step2FieldsSet++;
                } else  if(!(Validation.isNonEmptyStr(formBusinessData.getRelaventExperience()) &&
                        Integer.parseInt(formBusinessData.getRelaventExperience()) >= 0)) {
                    AndroidUtils.showSnackBar(registrationLayout, "Please Select Relavent Experience");
                    step2FieldsSet++;
                }
                if(step2FieldsSet == 0){
                    stepNo = 3;
                }

            } else if (stepNo == 3) {
                step1FieldsSet = 0;
                if(!(Validation.isNonEmptyStr(formBusinessData.getBankName()) &&
                        Integer.parseInt(formBusinessData.getBankName()) >= 0)) {
                    AndroidUtils.showSnackBar(registrationLayout, "Please Select Bank Name");
                    step3FieldsSet++;
                } else if (Validation.isEmptyStr(formBusinessData.getAccountNumber())) {
                    putError(12);
                    step3FieldsSet++;
                } else if (Validation.isEmptyStr(formBusinessData.getBranchCode())) {
                    putError(13);
                    step3FieldsSet++;
                } else if (Validation.isEmptyStr(formBusinessData.getBranchName())) {
                    putError(14);
                    step3FieldsSet++;
                } else if (Validation.isEmptyStr(formBusinessData.getIfscCode())) {
                    putError(15);
                    step3FieldsSet++;
                } else if (Validation.isEmptyStr(formBusinessData.getMicrCode())) {
                    putError(16);
                    step3FieldsSet++;
                } else if (Validation.isEmptyStr(formBusinessData.getAccountHolderName())) {
                    putError(17);
                    step3FieldsSet++;
                } else if (!Validation.isValidNumber(formBusinessData.getRegisteredMobileWithBank(), Validation.getNumberPrefix(formBusinessData.getRegisteredMobileWithBank()))) {
                    putError(18);
                    step3FieldsSet++;
                }

            }
        }
        AndroidUtils.showSnackBar(registrationLayout, "Please fill the registration form");
    }

    private void putError(int id) {
        Log.e("reach", "       )))))))))" + id);
        switch (id) {
            case 0:
                et_first_name.setError("First Name Can't be empty");
                break;
            case 1:
                et_last_name.setError("Last Name Can't be empty");
                break;
            case 2:
                et_email.setError("Please Enter Valid Email");
                break;
            case 3:
                et_mobile.setError("Please Enter Valid Mobile Number");
                break;
            case 4:
                et_password.setError("Password must be greater than 6 digits");
                break;
            case 5:
                et_confirm_password.setError("Password did not matched");
                break;
            case 6:
                etDOB.setError("Please Select Date");
                break;
            case 7:
                ((TextView) findViewById(R.id.tv_agreement)).setError("Please Accept Terms & Conditions");
                break;
            case 9:
                et_address.setError("Address Can't be empty");
                break;
            case 10:
                et_father_name.setError("Father's Name Can't be empty");
                break;
            case 11:
                et_pincode.setError("Please Enter Valid PINCODE");
                break;
            case 12:
                et_account_no.setError("Please Enter Valid Account Number");
                break;
            case 13:
                et_branch_code.setError("Please Enter Branch Code");
                break;
            case 14:
                et_branch_name.setError("Please Enter Branch Name");
                break;
            case 15:
                et_ifsc_code.setError("Please Enter IFSC Code");
                break;
            case 16:
                et_micr_code.setError("Please Enter MICR Code");
                break;
            case 17:
                et_account_holder_name.setError("Please Enter Account Holder Name");
                break;
            case 18:
                et_registered_mobile_with_bank.setError("Please Enter Your Registered mobile number");
                break;

            default:
                break;
        }
    }

}
