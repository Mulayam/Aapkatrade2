package com.example.pat.aapkatrade.login;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Change_Font;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Validation;

public class Forgot_Password extends AppCompatActivity implements View.OnClickListener {
    TextView tv_forgot_password, tv_forgot_password_description;
    Button btn_send_otp;
    EditText et_email_forgot, et_mobile_no;
    CoordinatorLayout activity_forgot__password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);
        initview();


    }

    private void initview() {

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

            showmessage("");
        } else {

        }


    }

    private void showmessage(String message) {
        AndroidUtils.showSnackBar(activity_forgot__password, message);


    }


    private void call_forgotpasswod_webservice() {

    }


}
