package com.example.pat.aapkatrade;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.general.ConnectivityNotFound;
import com.example.pat.aapkatrade.general.ConnetivityCheck;

public class MainActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;
    ConnetivityCheck connetivity_check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        connetivity_check=new ConnetivityCheck();
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {


                if(ConnetivityCheck.isNetworkAvailable(MainActivity.this))
                {
                    Intent mainIntent = new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
                else{
                    Intent mainIntent = new Intent(MainActivity.this,ConnectivityNotFound.class);
                    startActivity(mainIntent);
                    finish();
                }
                /* Create an Intent that will start the Menu-Activity. */

            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
