package com.example.pat.aapkatrade.user_dashboard.network;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.pat.aapkatrade.R;

public class NetworkActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

        setuptoolbar();

        setup_layout();

    }



    private void setuptoolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(null);
        //getSupportActionBar().setIcon(R.drawable.home_logo);
    }

    public void setup_layout(){



    }


}
