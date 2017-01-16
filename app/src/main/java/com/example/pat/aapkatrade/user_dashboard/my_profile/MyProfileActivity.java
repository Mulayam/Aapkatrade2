package com.example.pat.aapkatrade.user_dashboard.my_profile;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.user_dashboard.addcompany.AddCompany;
import com.example.pat.aapkatrade.user_dashboard.companylist.CompanyList;


public class MyProfileActivity extends AppCompatActivity
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

    private void setup_layout()
    {

        btnsave = (Button) findViewById(R.id.btnSave);

        btnEdit = (Button) findViewById(R.id.btnEdit);

        btnsave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(MyProfileActivity.this, CompanyList.class);
                startActivity(i);
            }
        });

       btnEdit.setOnClickListener(new View.OnClickListener()
       {
           @Override
           public void onClick(View v)
           {
               Intent i = new Intent(MyProfileActivity.this, AddCompany.class);
               startActivity(i);
           }
       });
    }


    private void setuptoolbar()
    {
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
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {

            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }


}
