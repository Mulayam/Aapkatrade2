package com.example.pat.aapkatrade;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.general.ConnectivityNotFound;
import com.example.pat.aapkatrade.general.ConnetivityCheck;

public class MainActivity extends AppCompatActivity
{

    private final int SPLASH_DISPLAY_LENGTH = 3000;
    ConnetivityCheck connetivity_check;
    TextView tv_aapkatrade;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);






        String fontPath = "impact.ttf";

        // text view label
        tv_aapkatrade = (TextView) findViewById(R.id.tv_aapkatrade);

        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);

        // Applying font
        tv_aapkatrade.setTypeface(tf);
        connetivity_check=new ConnetivityCheck();
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                if(ConnetivityCheck.isNetworkAvailable(MainActivity.this))
                {

                    Intent mainIntent = new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(mainIntent);
                    finish();
                    if(pd!=null)
                    {
                        pd.dismiss();
                    }
                }
                else
                {
                    Intent mainIntent = new Intent(MainActivity.this,ConnectivityNotFound.class);
                    mainIntent.putExtra("callerActivity", MainActivity.class.getName());
                    startActivity(mainIntent);
                    finish();
                    if(pd!=null)
                    {
                        pd.dismiss();
                    }
                }
                /* Create an Intent that will start the Menu-Activity. */

            }
        }, SPLASH_DISPLAY_LENGTH);
    }




}
