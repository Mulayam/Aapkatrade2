package com.example.pat.aapkatrade.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.Home.registration.RegistrationActivity;
import com.example.pat.aapkatrade.Home.registration.RegistrationBusinessAssociateActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.App_sharedpreference;
import com.example.pat.aapkatrade.general.Call_webservice;
import com.example.pat.aapkatrade.general.TaskCompleteReminder;
import com.example.pat.aapkatrade.general.Validation;
import com.google.gson.JsonObject;

import java.util.HashMap;


public class LoginActivity extends AppCompatActivity {
    TextView login_text, forgot_password;
    EditText username, password;
    RelativeLayout rl_login, relativeRegister;
    Validation vt;
    App_sharedpreference app_sharedpreference;
    CoordinatorLayout cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        app_sharedpreference = new App_sharedpreference(LoginActivity.this);
        InitView();
        putValues();

        relativeRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                Intent registerUserActivity = new Intent(LoginActivity.this, RegistrationActivity.class);
//                startActivity(registerUserActivity);


//
                if (app_sharedpreference.shared_pref != null) {
                    if (app_sharedpreference.getsharedpref("usertype", "0").equals("3")) {

                        Intent registerUserActivity = new Intent(LoginActivity.this, RegistrationBusinessAssociateActivity.class);
                        startActivity(registerUserActivity);
                    } else if ((app_sharedpreference.getsharedpref("usertype", "0").equals("1")) || app_sharedpreference.getsharedpref("usertype", "0").equals("2")) {
                        Intent registerUserActivity = new Intent(LoginActivity.this, RegistrationActivity.class);
                        startActivity(registerUserActivity);
                    }
                } else {
                    Log.e("null_sharedPreferences", "sharedPreferences");
                }






               /* if (sharedPreferences != null) {

                    if (sharedPreferences.getInt("user", 0) == 3) {

                if (app_sharedpreference.shared_pref!= null) {
                    if (app_sharedpreference.getsharedpref("usertype", "0").equals("3")) {

                        Intent registerUserActivity = new Intent(LoginActivity.this, RegistrationBusinessAssociateActivity.class);
                        startActivity(registerUserActivity);
                    } else if(app_sharedpreference.getsharedpref("usertype", "0").equals("1") || app_sharedpreference.getsharedpref("usertype", "0").equals("2") ) {
                        Intent registerUserActivity = new Intent(LoginActivity.this, RegistrationActivity.class);
                        startActivity(registerUserActivity);
                    }
                }

            }

*/


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

                        if (app_sharedpreference.shared_pref != null) {
                            if (app_sharedpreference.getsharedpref("usertype", "0").equals("3")) {

                                String login_url = "http://aapkatrade.com/slim/businesslogin";

                                callwebservice_login(login_url, input_username, input_password);


                            } else if (app_sharedpreference.getsharedpref("usertype", "0").equals("2")) {

                                String login_url = "http://aapkatrade.com/slim/buyerlogin";

                                callwebservice_login(login_url, input_username, input_password);


                            } else if (app_sharedpreference.getsharedpref("usertype", "0").equals("1")) {

                                String login_url = "http://aapkatrade.com/slim/sellerlogin";

                                callwebservice_login(login_url, input_username, input_password);


                            }
                        }


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

    private void callwebservice_login(String login_url, String input_username, String input_password) {

        // dialog.show();
        HashMap<String, String> webservice_body_parameter = new HashMap<>();
        webservice_body_parameter.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        webservice_body_parameter.put("type", "login");
        webservice_body_parameter.put("email", input_username);
        webservice_body_parameter.put("password", input_password);


        HashMap<String, String> webservice_header_type = new HashMap<>();
        webservice_header_type.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");


        Call_webservice.call_login_webservice(LoginActivity.this, login_url, "login", webservice_body_parameter, webservice_header_type);

        Call_webservice.taskCompleteReminder = new TaskCompleteReminder() {
            @Override
            public void Taskcomplete(JsonObject webservice_returndata) {

                Log.e("data2", webservice_returndata.toString());

                if (webservice_returndata != null) {
                    Log.e("webservice_returndata", webservice_returndata.toString());
                    JsonObject jsonObject = webservice_returndata.getAsJsonObject();

                    String error = jsonObject.get("error").getAsString();
                    String message = jsonObject.get("message").getAsString();
                    if (error.equals("false")) {
                        String user_id = jsonObject.get("user_id").getAsString();
                        String name = jsonObject.get("name").getAsString();

                        String email_id = jsonObject.get("email").getAsString();

                        showMessage(message);
                        save_shared_pref(user_id, name, email_id);

                        Intent Homedashboard = new Intent(LoginActivity.this, HomeActivity.class);
                        Homedashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(Homedashboard);

                    } else {
                        showMessage(message);
                    }


                }

            }
        };


    }

    public void save_shared_pref(String user_id, String user_name, String email_id) {
        app_sharedpreference.setsharedpref("userid", user_id);
        app_sharedpreference.setsharedpref("username", user_name);
        app_sharedpreference.setsharedpref("emailid", email_id);


    }

    private void InitView() {
        forgot_password = (TextView) findViewById(R.id.tv_forgotpassword);

        login_text = (TextView) findViewById(R.id.tv_login);
        cl = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        username = (EditText) findViewById(R.id.etusername);
        password = (EditText) findViewById(R.id.etpassword);
        rl_login = (RelativeLayout) findViewById(R.id.rl_login);
        relativeRegister = (RelativeLayout) findViewById(R.id.relativeRegister);
        vt = new Validation();


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

        snackbar.show();
    }


}
