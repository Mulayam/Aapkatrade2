package com.example.pat.aapkatrade.user_dashboard.my_company_profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;


public class MyCompanyProfile extends AppCompatActivity {

    TextView toolbar_title_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_company_profile);

        setuptoolbar();

    }


    private void setuptoolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        getSupportActionBar().setTitle("My Company Profile");

    }
}
