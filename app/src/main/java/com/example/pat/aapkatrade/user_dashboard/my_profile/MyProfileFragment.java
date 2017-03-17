package com.example.pat.aapkatrade.user_dashboard.my_profile;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.pat.aapkatrade.Home.registration.entity.City;
import com.example.pat.aapkatrade.Home.registration.entity.State;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpCityAdapter;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpStateAdapter;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.App_sharedpreference;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Utils.adapter.CustomSimpleListAdapter;
import com.example.pat.aapkatrade.general.Validation;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


/**
 * Created by PPC09 on 03-Mar-17.
 */

public class MyProfileFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private App_sharedpreference app_sharedpreference;
    private Context context;
    private EditText et_email, et_first_name, et_last_name, et_dob, et_father_name, et_mobile, et_ref_number, et_address, et_pincode, et_account_no, et_branch_code, et_branch_name, et_ifsc_code, et_micr_code, et_account_holder_name, et_registered_mobile_with_bank;
    private String state_id, stateName, city_id, qualification, total_exp, relevant_exp, id_proof, photo, bank_id;
    public int stepNumber;
    private Spinner spState, spcity, spQualification, spTotalExp, spRelExp, spSelectBank;
    private ImageView openCalander;
    private CheckBox agreement_check;
    private boolean isAgreementChecked = false;
    private RelativeLayout relativeAddress;
    public CardView step1aLayout, step1bLayout, step1cLayout, step2Layout, step3Layout;
    private ArrayList<State> stateList = new ArrayList<>();
    private ArrayList<City> cityList = new ArrayList<>();
    private ArrayList<String> qualificationList = new ArrayList<>(), totalExpList = new ArrayList<>(), relaventExpList = new ArrayList<>(), bankList = new ArrayList<>();
    ProgressBarHandler progressBarHandler;
    private LinearLayout updateMyProfileLayout;
    private HashMap<String, String> webservice_header_type = new HashMap<>();
    private int step1FieldsSet = -1, step2FieldsSet = -1, step3FieldsSet = -1;
    private File step1PhotoFile;
    private File step2PhotoFile;

    public static MyProfileFragment newInstance(int page, boolean isLast) {
        Bundle args = new Bundle();
        args.putInt("page", page);
        if (isLast)
            args.putBoolean("isLast", true);
        MyProfileFragment fragment = new MyProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        initView(view);
        loadEditText();
        openCalender();
        setUpSpinners();
        relativeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("call web", "call web service"+stepNumber);
                if (stepNumber+1 == 3) {
                    validateFields(stepNumber+1);
                    if (step3FieldsSet == 0) {
                        callUpdateWebService(0);
                    }
                }
            }
        });
        return view;
    }


    private void setUpSpinners() {
        setUpStateSpinner();
        setQualificationAdapter();
        setTotalExperienceAdapter();
        setRelaventExperienceAdapter();
        setBankListAdapter();
    }

    private void setUpStateSpinner() {
        Ion.with(context)
                .load("http://aapkatrade.com/slim/dropdown")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "state")
                .setBodyParameter("id", "101")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null) {
                            int selectedIndex = 0;
                            JsonArray jsonResultArray = result.getAsJsonArray("result");
                            stateList = new ArrayList<>();
                            stateList.add(new State("-1", "Please Select State"));
                            for (int i = 0; i < jsonResultArray.size(); i++) {
                                JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                                State stateEntity = new State(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString());
                                stateList.add(stateEntity);
                                if (state_id.equals(stateEntity.stateId)) {
                                    selectedIndex = i + 1;
                                    Log.e("HOooo434oooooo", jsonObject1.get("id").getAsString() + "  State Found  " + jsonObject1.get("name").getAsString());

                                }
                            }

                            Log.e("HOooooooooo", "State List Size : " + stateList.size());

                            SpStateAdapter spStateAdapter = new SpStateAdapter(context, stateList);
                            spState.setAdapter(spStateAdapter);
                            spState.setSelection(selectedIndex);
                            spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    state_id = stateList.get(position).stateId;
                                    setUpCitySpinner(state_id);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
                            Log.e("HOooooooooo", "State Not Found");
                            AndroidUtils.showSnackBar(updateMyProfileLayout, "State Not Found");
                        }
                    }
                });
    }

    private void setUpCitySpinner(String stateId) {
        Ion.with(context)
                .load("http://aapkatrade.com/slim/dropdown")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "city")
                .setBodyParameter("id", stateId)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null) {
                            int selectedIndex = 0;
                            JsonArray jsonResultArray = result.getAsJsonArray("result");
                            cityList = new ArrayList<>();
                            cityList.add(new City("-1", "Please Select City"));
                            for (int i = 0; i < jsonResultArray.size(); i++) {
                                JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                                City cityEntity = new City(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString());
                                cityList.add(cityEntity);
                                if (city_id.equals(cityEntity.cityId)) {
                                    selectedIndex = i + 1;
                                    Log.e("HOooo434oooooo", jsonObject1.get("id").getAsString() + "  State Found  " + jsonObject1.get("name").getAsString());
                                    Log.e("HOooooooooo", "selectedIndex @@: " + selectedIndex);
                                }
                            }

                            Log.e("HOooooooooo", "State List Size : " + stateList.size());
                            Log.e("HOooooooooo", "selectedIndex : " + selectedIndex);
                            SpCityAdapter spCityAdapter = new SpCityAdapter(context, cityList);
                            spcity.setAdapter(spCityAdapter);
                            spcity.setSelection(selectedIndex);
                            spcity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
                            Log.e("HOooooooooo", "State Not Found");
                            AndroidUtils.showSnackBar(updateMyProfileLayout, "State Not Found");
                        }
                    }
                });
    }

    private void openCalender() {
        openCalander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        MyProfileFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setMaxDate(now);
                dpd.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });

    }

    private void loadEditText() {
        et_email.setText(app_sharedpreference.getsharedpref("emailid"));
        et_first_name.setText(app_sharedpreference.getsharedpref("name"));
        et_last_name.setText(app_sharedpreference.getsharedpref("lname"));
        et_dob.setText(app_sharedpreference.getsharedpref("dob"));
        et_father_name.setText(app_sharedpreference.getsharedpref("father_name"));
        et_mobile.setText(app_sharedpreference.getsharedpref("mobile"));
        et_ref_number.setText(app_sharedpreference.getsharedpref("ref_no"));
        et_address.setText(app_sharedpreference.getsharedpref("address"));
        et_pincode.setText(app_sharedpreference.getsharedpref("pincode"));
        et_account_no.setText(app_sharedpreference.getsharedpref("account_no"));
        et_branch_code.setText(app_sharedpreference.getsharedpref("branch_code"));
        et_branch_name.setText(app_sharedpreference.getsharedpref("branch_name"));
        et_ifsc_code.setText(app_sharedpreference.getsharedpref("ifsc_code"));
        et_micr_code.setText(app_sharedpreference.getsharedpref("micr_code"));
        et_account_holder_name.setText(app_sharedpreference.getsharedpref("account_holder"));
        et_registered_mobile_with_bank.setText(app_sharedpreference.getsharedpref("register_mobile"));

        state_id = app_sharedpreference.getsharedpref("state_id");
        city_id = app_sharedpreference.getsharedpref("city_id");
        qualification = app_sharedpreference.getsharedpref("qualification");
        total_exp = app_sharedpreference.getsharedpref("total_exp");
        relevant_exp = app_sharedpreference.getsharedpref("relevant_exp");
        id_proof = app_sharedpreference.getsharedpref("id_proof");
        photo = app_sharedpreference.getsharedpref("photo");
        bank_id = app_sharedpreference.getsharedpref("bank_name");
        isAgreementChecked = Boolean.valueOf(app_sharedpreference.getsharedpref("term_accepted"));
    }

    private void initView(View view) {
        Log.e("page", "init" + stepNumber);
        context = getContext();
        progressBarHandler = new ProgressBarHandler(context);
        app_sharedpreference = new App_sharedpreference(context);
        updateMyProfileLayout = (LinearLayout) view.findViewById(R.id.updateMyProfileLayout);
        webservice_header_type.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        relativeAddress = (RelativeLayout) view.findViewById(R.id.relativeAddress);

        step1aLayout = (CardView) view.findViewById(R.id.step1aLayout);
        step1bLayout = (CardView) view.findViewById(R.id.step1bLayout);
        step1cLayout = (CardView) view.findViewById(R.id.step1cLayout);
        step2Layout = (CardView) view.findViewById(R.id.step2Layout);
        step3Layout = (CardView) view.findViewById(R.id.step3Layout);

        et_email = (EditText) view.findViewById(R.id.et_email);
        et_first_name = (EditText) view.findViewById(R.id.et_first_name);
        et_last_name = (EditText) view.findViewById(R.id.et_last_name);
        et_dob = (EditText) view.findViewById(R.id.et_dob);
        et_father_name = (EditText) view.findViewById(R.id.et_father_name);
        et_mobile = (EditText) view.findViewById(R.id.et_mobile);
        et_ref_number = (EditText) view.findViewById(R.id.et_ref_number);
        et_address = (EditText) view.findViewById(R.id.et_address);
        et_pincode = (EditText) view.findViewById(R.id.et_pincode);
        et_account_no = (EditText) view.findViewById(R.id.et_account_no);
        et_branch_code = (EditText) view.findViewById(R.id.et_branch_code);
        et_branch_name = (EditText) view.findViewById(R.id.et_branch_name);
        et_ifsc_code = (EditText) view.findViewById(R.id.et_ifsc_code);
        et_micr_code = (EditText) view.findViewById(R.id.et_micr_code);
        et_account_holder_name = (EditText) view.findViewById(R.id.et_account_holder_name);
        et_registered_mobile_with_bank = (EditText) view.findViewById(R.id.et_registered_mobile_with_bank);
        agreement_check = (CheckBox) view.findViewById(R.id.agreement_check);
        openCalander = (ImageView) view.findViewById(R.id.openCalander);

        spState = (Spinner) view.findViewById(R.id.spState);
        spcity = (Spinner) view.findViewById(R.id.spcity);
        spQualification = (Spinner) view.findViewById(R.id.spQualification);
        spTotalExp = (Spinner) view.findViewById(R.id.spTotalExp);
        spRelExp = (Spinner) view.findViewById(R.id.spRelExp);
        spSelectBank = (Spinner) view.findViewById(R.id.spSelectBank);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final int page = getArguments().getInt("page", 50);
        stepNumber = page;

        Log.e("page", "page number" + stepNumber + (step1aLayout == null));
        if (stepNumber == 0) {
            step1aLayout.setVisibility(View.VISIBLE);
            step1bLayout.setVisibility(View.VISIBLE);
            step1cLayout.setVisibility(View.VISIBLE);
            step2Layout.setVisibility(View.GONE);
            step3Layout.setVisibility(View.GONE);
        } else if (stepNumber == 1) {
            step1aLayout.setVisibility(View.GONE);
            step1bLayout.setVisibility(View.GONE);
            step1cLayout.setVisibility(View.GONE);
            step2Layout.setVisibility(View.VISIBLE);
            step3Layout.setVisibility(View.GONE);
        } else if (stepNumber == 2) {
            step1aLayout.setVisibility(View.GONE);
            step1bLayout.setVisibility(View.GONE);
            step1cLayout.setVisibility(View.GONE);
            step2Layout.setVisibility(View.GONE);
            step3Layout.setVisibility(View.VISIBLE);
        }
    }

    private void setBankListAdapter() {
        if (bankList != null && bankList.size() > 0) {
            bankList.clear();
        }
        progressBarHandler.show();
        bankList.add("Please Select Bank Name");
        Ion.with(context)
                .load("https://aapkatrade.com/slim/bank_list")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressBarHandler.hide();
                        if (result == null) {
                            showMessage("Bank Name Data is Null.");
                            Log.e("bankList", "Bank Name Data is Null.");
                        } else {
                            if (result.get("error").getAsString().equals(String.valueOf(false))) {
                                Log.e("hidcs", "match" + bank_id);
                                int selectedIndex = 0;
                                JsonArray jsonResultArray = result.getAsJsonArray("result");
                                for (int i = 0; i < jsonResultArray.size(); i++) {
                                    JsonObject jsonObject = (JsonObject) jsonResultArray.get(i);
                                    bankList.add(jsonObject.get("name").getAsString());
                                    if (bank_id.equals(jsonObject.get("id").getAsString())) {
                                        selectedIndex = i + 1;
                                        Log.e("bank_id", bank_id + "   ///     " + selectedIndex);
                                    }
                                    Log.e("bank_id123", bank_id + "   ///     " + jsonObject.get("id").getAsString());
                                }
                                CustomSimpleListAdapter bankListAdapter = new CustomSimpleListAdapter(context, bankList);
                                spSelectBank.setAdapter(bankListAdapter);
                                Log.e("bank_id12", bank_id + "        " + selectedIndex);
                                spSelectBank.setSelection(selectedIndex);
                                spSelectBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        if (position > 0) {
                                            bank_id = String.valueOf(position);
                                        }
                                        Log.e("bankList", "Selected Bank is " + bankList.get(position) + "     " + System.currentTimeMillis());
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            } else {
                                String msg = result.get("message").getAsString();
                                showMessage(msg);
                                Log.e("bankList", msg);
                            }
                        }
                    }
                });
    }

    private void setQualificationAdapter() {
        if (qualificationList != null && qualificationList.size() > 0) {
            qualificationList.clear();
        }
        progressBarHandler.show();
        qualificationList.add("Please Select Qualification");
        CustomSimpleListAdapter qualificationAdapter = new CustomSimpleListAdapter(context, qualificationList);
        spQualification.setAdapter(qualificationAdapter);
        Ion.with(context)
                .load("https://aapkatrade.com/slim/qualification")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressBarHandler.hide();
                        if (result == null) {
                            showMessage("Qualification Data is Null.");
                            Log.e("qualification", "Qualification Data is Null.");
                        } else {
                            if (result.get("error").getAsString().equals(String.valueOf(false))) {
                                JsonArray jsonResultArray = result.getAsJsonArray("result");
                                int selectedIndex = 0;
                                for (int i = 0; i < jsonResultArray.size(); i++) {
                                    JsonObject jsonObject = (JsonObject) jsonResultArray.get(i);
                                    qualificationList.add(jsonObject.get("name").getAsString());
                                    if (qualification.equals(jsonObject.get("name").getAsString())) {
                                        selectedIndex = i;
                                    }
                                }
                                CustomSimpleListAdapter qualificationAdapter = new CustomSimpleListAdapter(context, qualificationList);
                                spQualification.setAdapter(qualificationAdapter);
                                spQualification.setSelection(selectedIndex + 1);
                                spQualification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        Log.e("hi", "Selected Qualification is " + qualificationList.get(position) + System.currentTimeMillis());
                                        if (position >= 1) {
                                            qualification = qualificationList.get(position);
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            } else {
                                String msg = result.get("message").getAsString();
                                showMessage(msg);
                                Log.e("qualification", msg);
                            }
                        }
                    }
                });


    }

    private void setTotalExperienceAdapter() {
        if (totalExpList != null && totalExpList.size() > 0) {
            totalExpList.clear();
        }
        progressBarHandler.show();
        totalExpList.add("Please Select Total Experience");
        CustomSimpleListAdapter totalExpAdapter = new CustomSimpleListAdapter(context, totalExpList);
        spTotalExp.setAdapter(totalExpAdapter);


        Ion.with(context)
                .load("https://aapkatrade.com/slim/total_experience")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressBarHandler.hide();
                        if (result == null) {
                            showMessage("Total Exp Data is Null.");
                            Log.e("totalExp", "Total Exp Data is Null.");
                        } else {
                            if (result.get("error").getAsString().equals(String.valueOf(false))) {
                                JsonArray jsonResultArray = result.getAsJsonArray("result");
                                Log.e("totalExp", "Total Exp Data ." + total_exp);
                                int selectedIndex = 0;
                                for (int i = 0; i < jsonResultArray.size(); i++) {
                                    JsonObject jsonObject = (JsonObject) jsonResultArray.get(i);
                                    totalExpList.add(jsonObject.get("name").getAsString());
                                    if (total_exp.equals(jsonObject.get("name").getAsString())) {
                                        selectedIndex = i;
                                        Log.e("selectedIndex", jsonObject.get("name").getAsString() + "******" + total_exp);
                                    }
                                }
                                CustomSimpleListAdapter totalExpAdapter = new CustomSimpleListAdapter(context, totalExpList);
                                spTotalExp.setAdapter(totalExpAdapter);
                                spTotalExp.setSelection(selectedIndex + 1);
                                spTotalExp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                        Log.e("totalExp", "Selected Total Experience is " + qualificationList.get(position)+"     " + System.currentTimeMillis());
                                        if (position >= 1) {
//                                            total_experience = totalExpList.get(position);
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            } else {
                                String msg = result.get("message").getAsString();
                                showMessage(msg);
                                Log.e("totalExp", msg);
                            }
                        }
                    }
                });

    }

    private void setRelaventExperienceAdapter() {
        if (relaventExpList != null && relaventExpList.size() > 0) {
            relaventExpList.clear();
        }
        progressBarHandler.show();
        relaventExpList.add("Please Select Relavent Experience");
        CustomSimpleListAdapter relaventExpAdapter = new CustomSimpleListAdapter(context, relaventExpList);
        spRelExp.setAdapter(relaventExpAdapter);


        Ion.with(context)
                .load("https://aapkatrade.com/slim/relevant_experience")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressBarHandler.hide();
                        if (result == null) {
                            showMessage("Relavent Exp Data is Null.");
                            Log.e("relaventExpList", "Relavent Exp Data is Null.");
                        } else {
                            int previous_index = 0;
                            if (result.get("error").getAsString().equals(String.valueOf(false))) {
                                JsonArray jsonResultArray = result.getAsJsonArray("result");
                                Log.e("relExp", "Rel Exp Data ." + relevant_exp);
                                for (int i = 0; i < jsonResultArray.size(); i++) {
                                    JsonObject jsonObject = (JsonObject) jsonResultArray.get(i);
                                    relaventExpList.add(jsonObject.get("experience").getAsString());
                                    if (relevant_exp.equals(jsonObject.get("experience").getAsString())) {


                                        previous_index = i;

                                        Log.e("relExp", "Rel Exp Data ========." + relevant_exp);
                                        //spRelExp.setSelection(i);
                                    }
                                }
                                CustomSimpleListAdapter relaventExpAdapter = new CustomSimpleListAdapter(context, relaventExpList);
                                spRelExp.setAdapter(relaventExpAdapter);
                                spRelExp.setSelection(previous_index);
                                spRelExp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        Log.e("relaventExpList", "Selected Relavent Experience is " + relaventExpList.get(position) + "     " + System.currentTimeMillis());
                                        if (position >= 1) {
                                            relevant_exp = relaventExpList.get(position);
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            } else {
                                String msg = result.get("message").getAsString();
                                showMessage(msg);
                                Log.e("relaventExpList", msg);
                            }
                        }
                    }
                });

    }

    private void showMessage(String s) {
        AndroidUtils.showSnackBar(updateMyProfileLayout, s);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        showDate(year, monthOfYear + 1, dayOfMonth);
    }

    private void showDate(int year, int month, int day) {
        et_dob.setTextColor(ContextCompat.getColor(context, R.color.black));
        et_dob.setText(new StringBuilder().append(year).append("-").append(month).append("-").append(day));
    }

    private void validateFields(int stepNo) {
        if (stepNo == 1) {
            step1FieldsSet = 0;
            if (!Validation.isValidEmail(et_email.getText() != null ? et_email.getText().toString() : "")) {
                putError(2);
                step1FieldsSet++;
            } else if (Validation.isEmptyStr(et_first_name.getText() != null ? et_first_name.getText().toString() : "")) {
                putError(0);
                step1FieldsSet++;
            } else if (Validation.isEmptyStr(et_last_name.getText() != null ? et_last_name.getText().toString() : "")) {
                putError(1);
                step1FieldsSet++;
            } else if (Validation.isEmptyStr(et_father_name.getText() != null ? et_father_name.getText().toString() : "")) {
                putError(10);
                step1FieldsSet++;
            } else if (!Validation.isValidNumber(et_mobile.getText() != null ? et_mobile.getText().toString() : "", Validation.getNumberPrefix(et_mobile.getText() != null ? et_mobile.getText().toString() : ""))) {
                putError(3);
                step1FieldsSet++;
            } else if (!Validation.isValidDOB(et_dob.getText() != null ? et_dob.getText().toString() : "")) {
                putError(6);
                step1FieldsSet++;
            } else if (Validation.isEmptyStr(et_address.getText() != null ? et_address.getText().toString() : "")) {
                putError(9);
                step1FieldsSet++;
            } else if (!(Validation.isNonEmptyStr(state_id) &&
                    Integer.parseInt(state_id) > 0)) {
                AndroidUtils.showSnackBar(updateMyProfileLayout, "Please Select State");
                step1FieldsSet++;
            } else if (!(Validation.isNonEmptyStr(city_id) &&
                    Integer.parseInt(city_id) > 0)) {
                AndroidUtils.showSnackBar(updateMyProfileLayout, "Please Select City");
                step1FieldsSet++;
            } else if (!Validation.isPincode(et_pincode.getText() != null ? et_pincode.getText().toString() : "")) {
                putError(11);
                step1FieldsSet++;
            } else if (!agreement_check.isChecked()) {
                putError(7);
                step1FieldsSet++;
//            } else if (step1PhotoFile.getAbsolutePath().equals("/")) {
//                showMessage("Please Upload File");
//                step1FieldsSet++;
            }
            Log.e("hi", "step1FieldsSet=" + step1FieldsSet);

            if (step1FieldsSet == 0) {
                stepNumber = 2;
            }
        } else if (stepNo == 2) {
            step2FieldsSet = 0;
            if (Validation.isEmptyStr(qualification) ||
                    qualification.equals(qualificationList.get(0))) {
                AndroidUtils.showSnackBar(updateMyProfileLayout, "Please Select Qualification");
                step2FieldsSet++;
            } else if (Validation.isEmptyStr(total_exp) ||
                    total_exp.equals(totalExpList.get(0))) {
                AndroidUtils.showSnackBar(updateMyProfileLayout, "Please Select Total Experience");
                step2FieldsSet++;
            } else if (Validation.isEmptyStr(relevant_exp) ||
                    relevant_exp.equals(relaventExpList.get(0))) {
                AndroidUtils.showSnackBar(updateMyProfileLayout, "Please Select Relavent Experience");
                step2FieldsSet++;
//            } else if ((step2PhotoFile == null) || step2PhotoFile.getAbsolutePath().equals("/")) {
//                showMessage("Please Upload File");
//                step2FieldsSet++;
            }
            if (step2FieldsSet == 0) {
                stepNumber = 3;
            }

        } else if (stepNo == 3) {
            step3FieldsSet = 0;
            if (Validation.isEmptyStr(bank_id)) {
                AndroidUtils.showSnackBar(updateMyProfileLayout, "Please Select Bank Name");
                step3FieldsSet++;
            } else if (!(Validation.isNonEmptyStr(et_account_no.getText() != null ? et_account_no.getText().toString() : "") &&
                    Validation.isNumber(et_account_no.getText() != null ? et_account_no.getText().toString() : ""))) {
                putError(12);
                step3FieldsSet++;
            } else if (Validation.isEmptyStr(et_branch_code.getText() != null ? et_branch_code.getText().toString() : "")) {
                putError(13);
                step3FieldsSet++;
            } else if (Validation.isEmptyStr(et_branch_name.getText() != null ? et_branch_name.getText().toString() : "")) {
                putError(14);
                step3FieldsSet++;
            } else if (Validation.isEmptyStr(et_ifsc_code.getText() != null ? et_ifsc_code.getText().toString() : "")) {
                putError(15);
                step3FieldsSet++;
            } else if (Validation.isEmptyStr(et_micr_code.getText() != null ? et_micr_code.getText().toString() : "")) {
                putError(16);
                step3FieldsSet++;
            } else if (Validation.isEmptyStr(et_account_holder_name.getText() != null ? et_account_holder_name.getText().toString() : "")) {
                putError(17);
                step3FieldsSet++;
            } else if (!Validation.isValidNumber(et_registered_mobile_with_bank.getText() != null ? et_registered_mobile_with_bank.getText().toString() : "", Validation.getNumberPrefix(et_registered_mobile_with_bank.getText() != null ? et_registered_mobile_with_bank.getText().toString() : ""))) {
                putError(18);
                step3FieldsSet++;
            }
        }
    }

    private void putError(int id) {
        Log.e("reach", "       )))))))))" + id);
        switch (id) {
            case 0:
                et_first_name.setError("First Name Can't be empty");
                showMessage("First Name Can't be empty");
                break;
            case 1:
                et_last_name.setError("Last Name Can't be empty");
                showMessage("Last Name Can't be empty");
                break;
            case 2:
                et_email.setError("Please Enter Valid Email");
                showMessage("Please Enter Valid Email");
                break;
            case 3:
                et_mobile.setError("Please Enter Valid Mobile Number");
                showMessage("Please Enter Valid Mobile Number");
                break;
            case 6:
                et_dob.setError("Please Select Date");
                showMessage("Please Select Date");
                break;
            case 9:
                et_address.setError("Address Can't be empty");
                showMessage("Address Can't be empty");
                break;
            case 10:
                et_father_name.setError("Father's Name Can't be empty");
                showMessage("Father's Name Can't be empty");
                break;
            case 11:
                et_pincode.setError("Please Enter Valid PINCODE");
                showMessage("Please Enter Valid PINCODE");
                break;
            case 12:
                et_account_no.setError("Please Enter Valid Account Number");
                showMessage("Please Enter Valid Account Number");
                break;
            case 13:
                et_branch_code.setError("Please Enter Branch Code");
                showMessage("Please Enter Branch Code");
                break;
            case 14:
                et_branch_name.setError("Please Enter Branch Name");
                showMessage("Please Enter Branch Name");
                break;
            case 15:
                et_ifsc_code.setError("Please Enter IFSC Code");
                showMessage("Please Enter IFSC Code");
                break;
            case 16:
                et_micr_code.setError("Please Enter MICR Code");
                showMessage("Please Enter MICR Code");
                break;
            case 17:
                et_account_holder_name.setError("Please Enter Account Holder Name");
                showMessage("Please Enter Account Holder Name");
                break;
            case 18:
                et_registered_mobile_with_bank.setError("Please Enter Your Registered mobile number");
                showMessage("Please Enter Your Registered mobile number");
                break;
            default:
                break;
        }
    }

    private void callUpdateWebService(int stepNo) {

        progressBarHandler.show();
        Ion.with(context)
                .load((new StringBuilder()).append(getResources().getString(R.string.webservice_base_url)).append("/bank_detail_update").toString())
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("bank_name", bank_id)
                .setBodyParameter("account_no", et_account_no.getText().toString())
                .setBodyParameter("account_holder", et_account_holder_name.getText().toString())
                .setBodyParameter("branch_code", et_branch_code.getText().toString())
                .setBodyParameter("branch_name", et_branch_name.getText().toString())
                .setBodyParameter("ifsc_code", et_ifsc_code.getText().toString())
                .setBodyParameter("micr_code", et_micr_code.getText().toString())
                .setBodyParameter("register_mobile", et_registered_mobile_with_bank.getText().toString())
                .setBodyParameter("user_id", app_sharedpreference.getsharedpref("userid"))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressBarHandler.hide();
                        if (result == null) {
                            Log.e("result##", "null123");
                            AndroidUtils.showSnackBar(updateMyProfileLayout, "Some Error Occurred, Please Try Again");
                        } else {
                            Log.e("result##", "not null123");
                            if( result.get("message").getAsString().equals("Success")){
                                Log.e("result##", "Successfully saved");
                            }
                        }
                    }
                });

    }
}



