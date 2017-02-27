package com.example.pat.aapkatrade.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.registration.RegistrationActivity;
import com.example.pat.aapkatrade.Home.registration.RegistrationBusinessAssociateActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.App_config;
import com.example.pat.aapkatrade.general.App_sharedpreference;
import com.example.pat.aapkatrade.general.Call_webservice;
import com.example.pat.aapkatrade.general.Change_Font;
import com.example.pat.aapkatrade.general.TaskCompleteReminder;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Validation;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.search.Search;
import com.google.gson.JsonObject;

import java.util.HashMap;

public class Forgot_Password extends AppCompatActivity implements View.OnClickListener {
    TextView tv_forgot_password, tv_forgot_password_description;
    Button btn_send_otp;
    EditText et_email_forgot, et_mobile_no;
    CoordinatorLayout activity_forgot__password;
    App_sharedpreference app_sharedpreference;
    String usertype;
    ProgressBarHandler progressBarHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);
        initview();


    }

    private void initview() {
        app_sharedpreference = new App_sharedpreference(Forgot_Password.this);
        progressBarHandler=new ProgressBarHandler(Forgot_Password.this);

        tv_forgot_password = (TextView) findViewById(R.id.tv_forgot_password);
        tv_forgot_password_description = (TextView) findViewById(R.id.tv_forgot_password_description);


        et_email_forgot = (EditText) findViewById(R.id.et_email_forgot);
        et_mobile_no = (EditText) findViewById(R.id.et_mobile_no);

        btn_send_otp = (Button) findViewById(R.id.btn_send_otp);
        btn_send_otp.setOnClickListener(this);

        activity_forgot__password = (CoordinatorLayout) findViewById(R.id.activity_forgot__password);

        Change_Font.Change_Font_textview(Forgot_Password.this, tv_forgot_password);
        Change_Font.Change_Font_textview(Forgot_Password.this, tv_forgot_password_description);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_send_otp:


                Validatefields();


                break;
        }

    }

    private void Validatefields() {

        if (Validation.validateEdittext(et_email_forgot)) {
            call_forgotpasswod_webservice();

        } else if (Validation.validateEdittext(et_mobile_no)) {

            call_forgotpasswod_webservice();


        }
        else {
            showmessage("");

        }


    }

    private void showmessage(String message) {
        AndroidUtils.showSnackBar(activity_forgot__password, message);


    }


    private void call_forgotpasswod_webservice() {
        progressBarHandler.show();



            String webservice_forgot_password = "http://aapkatrade.com/slim/forget";

            if (app_sharedpreference.shared_pref != null) {
                if (app_sharedpreference.getsharedpref("usertype", "0").equals("3")) {

                    usertype = "business";
                } else if ((app_sharedpreference.getsharedpref("usertype", "0").equals("1"))) {
                    usertype = "seller";

                } else if (app_sharedpreference.getsharedpref("usertype", "0").equals("2")) {
                    usertype = "business";

                }
            } else {
                Log.e("null_sharedPreferences", "sharedPreferences");
            }


            HashMap<String, String> webservice_body_parameter = new HashMap<>();
            webservice_body_parameter.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
            webservice_body_parameter.put("type", usertype);
            webservice_body_parameter.put("email", et_email_forgot.getText().toString().trim());
            webservice_body_parameter.put("mobile", et_mobile_no.getText().toString().trim());
            webservice_body_parameter.put("client_id", App_config.getCurrentDeviceId(Forgot_Password.this));


            HashMap<String, String> webservice_header_type = new HashMap<>();
            webservice_header_type.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");


            Call_webservice.forgot_password(Forgot_Password.this, webservice_forgot_password, "forgot_password", webservice_body_parameter, webservice_header_type);
            Call_webservice.taskCompleteReminder=new TaskCompleteReminder() {
                @Override
                public void Taskcomplete(JsonObject data) {
                    if(data!=null) {
                        String error = data.get("error").getAsString();
                        if(error.contains("false"))
                        {
                            Intent go_to_activity_otp_verify =new Intent(Forgot_Password.this,ActivityOTPVerify.class);
                            startActivity(go_to_activity_otp_verify);
                        }
                        String message = data.get("message").getAsString();
                        showmessage(message);

                        progressBarHandler.hide();
                    }
                    else {
                        progressBarHandler.hide();
                    }


Log.e("forgot_password",data.toString());



                }
            };



    }
}
