package com.example.pat.aapkatrade.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.registration.RegistrationActivity;
import com.example.pat.aapkatrade.Home.registration.RegistrationBusinessAssociateActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Validation;


public class LoginActivity extends AppCompatActivity {
    TextView login_text,forgot_password;
    EditText username, password;
    RelativeLayout rl_login, relativeRegister;
    Validation vt;

    CoordinatorLayout cl;
    private static String shared_pref_name = "aapkatrade";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InitView();
        putValues();

        relativeRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent registerUserActivity = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(registerUserActivity);

              /*  if (sharedPreferences != null) {
                    if (sharedPreferences.getInt("user", 0) == 3) {
                        Intent registerUserActivity = new Intent(LoginActivity.this, RegistrationBusinessAssociateActivity.class);
                        startActivity(registerUserActivity);
                    } else if((sharedPreferences.getInt("user", 0) == 1) || (sharedPreferences.getInt("user", 0) == 2)) {
                        Intent registerUserActivity = new Intent(LoginActivity.this, RegistrationActivity.class);
                        startActivity(registerUserActivity);
                    }
                }*/
            }
        });
    }

    private void putValues() {

        login_text.setTextSize(getResources().getDimension(R.dimen.textsize));
        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String input_username = username.getText().toString().trim();
                String input_password = password.getText().toString().trim();

                if (Validation.validate_edittext(username)) {


                    if (Validation.validate_edittext(password)) {

                        Intent activityOTPVerify = new Intent(LoginActivity.this, ActivityOTPVerify.class);
                        startActivity(activityOTPVerify);

                    } else {
                        showMessage("");
                        password.setError("Invalid Password");
                    }


                } else {
                    username.setError("");
                    showMessage("Invalid Username");
                }

            }
        });

    }

    private void InitView() {
        forgot_password=(TextView)findViewById(R.id.tv_forgotpassword);

        login_text = (TextView) findViewById(R.id.tv_login);
        cl = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        username = (EditText) findViewById(R.id.etusername);
        password = (EditText) findViewById(R.id.etpassword);
        rl_login = (RelativeLayout) findViewById(R.id.rl_login);
        relativeRegister = (RelativeLayout) findViewById(R.id.relativeRegister);
        vt = new Validation();
        sharedPreferences = getSharedPreferences(shared_pref_name, MODE_PRIVATE);


        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotpassword_activity = new Intent(LoginActivity.this, Forgot_Password.class);
                startActivity(forgotpassword_activity);
            }
        });

    }

    public void showMessage(String message) {

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






}
