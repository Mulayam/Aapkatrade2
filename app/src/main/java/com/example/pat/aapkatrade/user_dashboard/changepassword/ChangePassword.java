package com.example.pat.aapkatrade.user_dashboard.changepassword;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.registration.RegistrationActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.ConnectivityNotFound;
import com.example.pat.aapkatrade.general.ConnetivityCheck;
import com.example.pat.aapkatrade.login.ActivityOTPVerify;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


public class ChangePassword extends AppCompatActivity {

    private EditText etOldPassword, etNewPassword, etConfirmPassword;
    private Button saveNewPasswordButton;
    private ChangePasswordData changePasswordData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setuptoolbar();
        initView();
        loadData();
        savePassword();

    }

    private void savePassword() {
        saveNewPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callChangePasswordWebService("seller", changePasswordData);
            }
        });
    }

    private void loadData() {
        changePasswordData.setOldPassword(etOldPassword.getText().toString());
        changePasswordData.setNewPassword(etNewPassword.getText().toString());
        changePasswordData.setConfirmPassword(etConfirmPassword.getText().toString());
    }

    private void callChangePasswordWebService(String userType, ChangePasswordData data) {
        if(ConnetivityCheck.isNetworkAvailable(ChangePassword.this)) {
            Ion.with(ChangePassword.this)
                    .load("http://aapkatrade.com/slim/changePassword")
                    .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                    .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                    .setBodyParameter("type", userType)
                    .setBodyParameter("old_password", data.getOldPassword())
                    .setBodyParameter("new_password", data.getNewPassword())
                    .setBodyParameter("confirm_password", data.getConfirmPassword())
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {

                            Log.e("data", result.toString());

                        }

                    });
        }else {
            Intent intent = new Intent(ChangePassword.this, ConnectivityNotFound.class);
            intent.putExtra("callerActivity", ChangePassword.class.getName());
            startActivity(intent);
        }
    }

    private void initView() {
        etOldPassword = (EditText) findViewById(R.id.etOldPassword);
        etNewPassword = (EditText) findViewById(R.id.etNewPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etNewPasswordConfirm);
        saveNewPasswordButton = (Button) findViewById(R.id.saveNewPasswordButton);
        changePasswordData = new ChangePasswordData();
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
}
