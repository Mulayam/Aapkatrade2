package com.example.pat.aapkatrade.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.user_dashboard.User_DashboardFragment;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LoginDashboard extends AppCompatActivity {
    private FrameLayout fl_seller, fl_buyer, fl_business_assoc;
    private AppSharedPreference appSharedPreference;
    private Context context;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.activity_logindashboard);
        context = LoginDashboard.this;
        appSharedPreference =new AppSharedPreference(this);
        setUpToolBar();
        initView();

        fl_seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(appSharedPreference.shared_pref!= null)
                    appSharedPreference.setsharedpref("usertype","1");

                Intent i = new Intent(LoginDashboard.this, LoginActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        });

        fl_buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(appSharedPreference.shared_pref!= null)
                    appSharedPreference.setsharedpref("usertype","2");
                Intent i = new Intent(LoginDashboard.this, LoginActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        });


        fl_business_assoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(appSharedPreference.shared_pref!= null)
                    appSharedPreference.setsharedpref("usertype","3");
                Intent i = new Intent(LoginDashboard.this, LoginActivity.class);
                startActivity(i);



                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        });


    }

    private void initView() {
        fl_seller = (FrameLayout) findViewById(R.id.fl_seller);
        fl_buyer = (FrameLayout) findViewById(R.id.fl_buyer);
        fl_business_assoc = (FrameLayout) findViewById(R.id.fl_business_assoc);
        fl_seller = (FrameLayout) findViewById(R.id.fl_seller);

        fl_seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LoginDashboard.this, User_DashboardFragment.class);


                startActivity(i);
            }
        });


    }


    private void setUpToolBar() {
        ImageView homeIcon = (ImageView) findViewById(R.id.iconHome) ;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AndroidUtils.setImageColor(homeIcon, context, R.color.white);
        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, HomeActivity.class));
            }
        });
        AndroidUtils.setBackgroundSolid(toolbar, context, R.color.transparent, 0);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setElevation(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }


}
