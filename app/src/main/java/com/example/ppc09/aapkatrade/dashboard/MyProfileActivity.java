package com.example.ppc09.aapkatrade.dashboard;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ppc09.aapkatrade.R;


public class MyProfileActivity extends AppCompatActivity implements View.OnClickListener
{

    TextView  toolbar_title_txt;
    Button btnsave,btnEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_profile);

         setuptoolbar();

        setup_layout();

    }

    private void setup_layout() {

        btnsave = (Button) findViewById(R.id.btnSave);

        btnEdit = (Button) findViewById(R.id.btnEdit);

        btnsave.setOnClickListener(this);

        btnEdit.setOnClickListener(this);
    }




    private void setuptoolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar_title_txt = (TextView) findViewById(R.id.title_txt);

        toolbar_title_txt.setText("My Profile");

    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId()){

            case R.id.btnEdit:
                Intent i = new Intent(MyProfileActivity.this, AddCompany.class);
                startActivity(i);


            case R.id.btnSave:
                Intent n = new Intent(MyProfileActivity.this, MyCompanyProfile.class);
                startActivity(n);


        }

    }
}
