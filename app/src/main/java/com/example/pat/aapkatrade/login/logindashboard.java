package com.example.pat.aapkatrade.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.user_dashboard.DashboardActivity;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class logindashboard extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */


    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */

FrameLayout fl_seller,fl_buyer,fl_business_assoc;


    FrameLayout fl_seller ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_logindashboard);
        Initview();

        fl_seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(logindashboard.this,loginActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        });

        fl_buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(logindashboard.this,loginActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        });


        fl_business_assoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(logindashboard.this,loginActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        });










    }

    private void Initview() {
        fl_seller=(FrameLayout)findViewById(R.id.fl_seller)  ;
        fl_buyer=(FrameLayout)findViewById(R.id.fl_buyer)  ;

        fl_business_assoc=(FrameLayout)findViewById(R.id.fl_business_assoc)  ;

        fl_seller = (FrameLayout) findViewById(R.id.fl_seller);


        fl_seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(logindashboard.this, DashboardActivity.class);
                startActivity(i);

            }
        });


    }


}
