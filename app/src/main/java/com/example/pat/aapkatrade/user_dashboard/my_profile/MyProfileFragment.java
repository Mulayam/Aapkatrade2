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
import android.widget.Spinner;

import com.example.pat.aapkatrade.Home.registration.entity.City;
import com.example.pat.aapkatrade.Home.registration.entity.State;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpStateAdapter;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.App_sharedpreference;
import com.example.pat.aapkatrade.general.Call_webservice;
import com.example.pat.aapkatrade.general.TaskCompleteReminder;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

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
    private String state_id, city_id, qualification, total_exp, relevant_exp, id_proof, photo, bank_name;
    public int stepNumber;
    private Spinner spState, spcity, spQualification, spTotalExp, spRelExp, spSelectBank;
    private ImageView openCalander;
    private CheckBox agreement_check;
    private boolean isAgreementChecked = false;
    public CardView step1aLayout, step1bLayout, step1cLayout, step2Layout, step3Layout;
    private ArrayList<State> stateList = new ArrayList<>();
    private ArrayList<City> cityList = new ArrayList<>();
    private ArrayList<String> qualificationList = new ArrayList<>(), totalExpList = new ArrayList<>(), relaventExpList = new ArrayList<>(), bankList = new ArrayList<>();
    ProgressBarHandler progressBarHandler;
    private LinearLayout updateMyProfileLayout;
    private HashMap<String, String> webservice_header_type = new HashMap<>();

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
        return view;
    }

    private void setUpSpinners() {
        setUpStateSpinner();
    }

    private void setUpStateSpinner() {
        HashMap<String, String> webservice_body_parameter = new HashMap<>();
        webservice_body_parameter.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        webservice_body_parameter.put("type", "state");
        webservice_body_parameter.put("id", "101");

        Call_webservice.getcountrystatedata(context, "state", getResources().getString(R.string.webservice_base_url) + "/dropdown", webservice_body_parameter, webservice_header_type);

        Call_webservice.taskCompleteReminder = new TaskCompleteReminder() {
            @Override
            public void Taskcomplete(JsonObject state_data_webservice) {

                if (state_data_webservice != null) {
                    Log.e("Taskcomplete", "TaskcompleteError" + state_data_webservice.toString());
                    JsonObject jsonObject = state_data_webservice.getAsJsonObject();
                    JsonArray jsonResultArray = jsonObject.getAsJsonArray("result");
                    stateList.clear();
                    State stateEntity_init = new State("-1", "Please Select State");
                    stateList.add(stateEntity_init);

                    for (int i = 0; i < jsonResultArray.size(); i++) {
                        JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                        State stateEntity = new State(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString());
                        stateList.add(stateEntity);
                        if (stateList.get(i).stateId.equals(state_id)) {
                            spState.setSelection(i - 1);
                        }
                    }
                    SpStateAdapter spStateAdapter = new SpStateAdapter(context, stateList);
                    spState.setAdapter(spStateAdapter);

                    spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {
                            state_id = stateList.get(position).stateId;
                            cityList = new ArrayList<>();
                            if (position > 0) {
//                                getCity(stateList.get(position).stateId);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else {
                    AndroidUtils.showSnackBar(updateMyProfileLayout, "State Not Found");
                }
            }

        };
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
        bank_name = app_sharedpreference.getsharedpref("bank_name");
        isAgreementChecked = Boolean.valueOf(app_sharedpreference.getsharedpref("term_accepted"));
    }

    private void initView(View view) {
        Log.e("page", "init" + stepNumber);
        context = getContext();
        progressBarHandler = new ProgressBarHandler(context);
        app_sharedpreference = new App_sharedpreference(context);
        updateMyProfileLayout = (LinearLayout) view.findViewById(R.id.updateMyProfileLayout);
        webservice_header_type.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        showDate(year, monthOfYear + 1, dayOfMonth);
    }

    private void showDate(int year, int month, int day) {
        et_dob.setTextColor(ContextCompat.getColor(context, R.color.black));
        et_dob.setText(new StringBuilder().append(year).append("-").append(month).append("-").append(day));
    }

}
