package com.example.pat.aapkatrade;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.pat.aapkatrade.Home.Homeactivity;
import com.example.pat.aapkatrade.general.Connetivity_check;

public class MainActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;
    Connetivity_check connetivity_check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        connetivity_check=new Connetivity_check();
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {


//                if(Connetivity_check.isNetworkAvailable(MainActivity.this))
//                {
                    Intent mainIntent = new Intent(MainActivity.this,Homeactivity.class);
                    startActivity(mainIntent);
                    finish();
//                }
//                else{
//
//                }
                /* Create an Intent that will start the Menu-Activity. */

            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
