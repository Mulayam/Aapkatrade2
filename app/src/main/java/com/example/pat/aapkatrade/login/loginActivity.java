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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;


public class LoginActivity extends AppCompatActivity
{

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


            }


        });

    }


    private void putValues() {


        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String input_email = username.getText().toString().trim();
                String input_password = password.getText().toString().trim();

                if (Validation.validateEdittext(username)) {

                    if(Validation.isValidEmail(input_email)){


                    if (Validation.validateEdittext(password)) {


                        if (app_sharedpreference.shared_pref != null) {

                            Log.e("login------------", app_sharedpreference.getsharedpref("usertype", "0"));

                            if (app_sharedpreference.getsharedpref("usertype", "0").equals("3")) {

                                Log.e("login------------", app_sharedpreference.getsharedpref("usertype", "0"));

                                String login_url = "http://aapkatrade.com/slim/businesslogin";

                                callwebservice_login(login_url, input_email, input_password);


                            }
                            else if (app_sharedpreference.getsharedpref("usertype", "0").equals("2")) {

                                String login_url = "http://aapkatrade.com/slim/buyerlogin";

                                callwebservice_login(login_url, input_email, input_password);


                            }
                            else if (app_sharedpreference.getsharedpref("usertype", "0").equals("1")) {

                                String login_url = "http://aapkatrade.com/slim/sellerlogin";

                                callwebservice_login(login_url, input_email, input_password);

                            }
                        }
                    }
                    else {
                        showMessage("Invalid Password");
                        password.setError("Invalid Password");
                    }

                    } else {
                        showMessage("Invalid Password");
                        password.setError("Invalid Password");
                    }

                }
                else
                {
                    username.setError("Invalid Username");
                    showMessage("Invalid Username");
                }

            }
        });

    }

    private void callwebservice_login(String login_url, String input_username, String input_password)
    {
        // dialog.show();
        HashMap<String, String> webservice_body_parameter = new HashMap<>();
        webservice_body_parameter.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        webservice_body_parameter.put("type", "login");
        webservice_body_parameter.put("email", input_username);
        webservice_body_parameter.put("password", input_password);

        HashMap<String, String> webservice_header_type = new HashMap<>();
        webservice_header_type.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");

        Call_webservice.call_login_webservice(LoginActivity.this, login_url, "login", webservice_body_parameter, webservice_header_type);

        Call_webservice.taskCompleteReminder = new TaskCompleteReminder()
        {
            @Override
            public void Taskcomplete(JsonObject webservice_returndata)
            {

                if (webservice_returndata != null) {
                    if (app_sharedpreference.getsharedpref("usertype", "0").equals("1")) {

                        Log.e("webservice_returndata", webservice_returndata.toString());

                        JsonObject jsonObject = webservice_returndata.getAsJsonObject();

                        String error = jsonObject.get("error").getAsString();

                        String message = jsonObject.get("message").getAsString();

                        if (error.equals("false")) {
                            String user_id = jsonObject.get("user_id").getAsString();

                            String email_id = jsonObject.get("email").getAsString();

                            JsonArray jsonResultArray = jsonObject.getAsJsonArray("all_info");

                            for (int i = 0; i < jsonResultArray.size(); i++) {
                                JsonObject jsonobject_all_info = (JsonObject) jsonResultArray.get(i);

                                String name = jsonobject_all_info.get("name").getAsString();

                                String address = jsonobject_all_info.get("address").getAsString();

                                String lname = jsonobject_all_info.get("lastname").getAsString();


                             //   String dob = jsonobject_all_info.get("dob").getAsJsonNull().getAsString();

                                String mobile_no = jsonobject_all_info.get("mobile").getAsString();

                                System.out.println("name--" + name + "address--" + address + "lname--" + lname + "dob--" + "");

                                showMessage(message);

                                save_shared_pref(user_id, name, email_id, lname, "", address, mobile_no);

                                Intent Homedashboard = new Intent(LoginActivity.this, HomeActivity.class);

                                Homedashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                startActivity(Homedashboard);

                            }
                        } else {
                            showMessage(message);
                        }
                    }
                    else if(app_sharedpreference.getsharedpref("usertype", "0").equals("2"))
                    {

                        Log.e("webservice_returndata", webservice_returndata.toString());

                        JsonObject jsonObject = webservice_returndata.getAsJsonObject();

                        String error = jsonObject.get("error").getAsString();

                        String message = jsonObject.get("message").getAsString();

                        if (error.equals("false")) {
                            String user_id = jsonObject.get("user_id").getAsString();

                            String email_id = jsonObject.get("email").getAsString();

                            JsonArray jsonResultArray = jsonObject.getAsJsonArray("all_info");

                            for (int i = 0; i < jsonResultArray.size(); i++) {
                                JsonObject jsonobject_all_info = (JsonObject) jsonResultArray.get(i);

                                String name = jsonobject_all_info.get("name").getAsString();

                                String address = jsonobject_all_info.get("address").getAsString();

                                String lname = jsonobject_all_info.get("lastname").getAsString();



                                String mobile_no = jsonobject_all_info.get("mobile").getAsString();

                                System.out.println("name--" + name + "address--" + address + "lname--" + lname + "dob--" + "");

                                showMessage(message);

                                save_shared_pref(user_id, name, email_id, lname, "", address, mobile_no);

                                Intent Homedashboard = new Intent(LoginActivity.this, HomeActivity.class);

                                Homedashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                startActivity(Homedashboard);

                            }
                        } else {
                            showMessage(message);
                        }









                    }

                    else if(app_sharedpreference.getsharedpref("usertype", "0").equals("3"))
                    {

                        Log.e("webservice_returndata", webservice_returndata.toString());

                        JsonObject jsonObject = webservice_returndata.getAsJsonObject();

                        String error = jsonObject.get("error").getAsString();

                        String message = jsonObject.get("message").getAsString();

                        if (error.equals("false")) {
                            String user_id = jsonObject.get("user_id").getAsString();

                            String email_id = jsonObject.get("email").getAsString();

                            JsonArray jsonResultArray = jsonObject.getAsJsonArray("all_info");

                            for (int i = 0; i < jsonResultArray.size(); i++) {
                                JsonObject jsonobject_all_info = (JsonObject) jsonResultArray.get(i);

                                String name = jsonobject_all_info.get("name").getAsString();

                                String address = jsonobject_all_info.get("address").getAsString();

                                String lname = jsonobject_all_info.get("lastname").getAsString();



                                String mobile_no = jsonobject_all_info.get("mobile").getAsString();

                                System.out.println("name--" + name + "address--" + address + "lname--" + lname + "dob--" + "");

                                showMessage(message);

                                save_shared_pref(user_id, name, email_id, lname, "", address, mobile_no);

                                Intent Homedashboard = new Intent(LoginActivity.this, HomeActivity.class);

                                Homedashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                startActivity(Homedashboard);

                            }
                        } else {
                            showMessage(message);
                        }









                    }






                }
            }
        };

    }

    public void save_shared_pref(String user_id, String user_name, String email_id,String lname,String dob,String address,String mobile)
    {
        app_sharedpreference.setsharedpref("userid", user_id);
        app_sharedpreference.setsharedpref("username", user_name);
        app_sharedpreference.setsharedpref("emailid", email_id);
        app_sharedpreference.setsharedpref("lname",lname);
        app_sharedpreference.setsharedpref("dob",dob);
        app_sharedpreference.setsharedpref("address",address);
        app_sharedpreference.setsharedpref("mobile",mobile);


    }

    private void InitView()
    {
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

    public void showMessage(String message)
    {
        Snackbar snackbar = Snackbar
                .make(cl, message, Snackbar.LENGTH_LONG)
                .setAction("", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });

        snackbar.show();
    }


}
