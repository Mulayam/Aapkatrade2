package com.example.pat.aapkatrade.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Change_Font;

public class Forgot_Password extends AppCompatActivity {
    TextView tv_forgot_password,tv_forgot_password_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);
        tv_forgot_password=(TextView)findViewById(R.id.tv_forgot_password);
        tv_forgot_password_description=(TextView)findViewById(R.id.tv_forgot_password_description);




        Change_Font.Change_Font_textview(Forgot_Password.this,tv_forgot_password);
        Change_Font.Change_Font_textview(Forgot_Password.this,tv_forgot_password_description);




    }
}
