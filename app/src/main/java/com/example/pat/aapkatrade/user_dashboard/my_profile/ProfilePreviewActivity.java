package com.example.pat.aapkatrade.user_dashboard.my_profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.App_sharedpreference;
import com.example.pat.aapkatrade.user_dashboard.address.add_address.AddAddressActivity;
import com.example.pat.aapkatrade.user_dashboard.changepassword.ChangePassword;

public class ProfilePreviewActivity extends AppCompatActivity
{

    TextView tvTitle;
    LinearLayout linearLayoutLagout,linearLayoutResetpassword,linearLayoutAddress;
    App_sharedpreference app_sharedpreference;
    Button btnEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile_preview);

        app_sharedpreference = new App_sharedpreference(this);

        setup_layout();

        setuptoolbar();



    }

    private void setup_layout()
    {
        btnEdit = (Button) findViewById(R.id.btnEdit);

        linearLayoutLagout = (LinearLayout) findViewById(R.id.linearLayoutLagout);

        linearLayoutResetpassword = (LinearLayout) findViewById(R.id.linearLayoutResetpassword);

        linearLayoutAddress = (LinearLayout) findViewById(R.id.linearLayoutAddress);


        btnEdit.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                  Intent profile_edit = new Intent(ProfilePreviewActivity.this, MyProfileActivity.class);
                  startActivity(profile_edit);
            }


        });

        linearLayoutLagout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                save_shared_pref("notlogin", "notlogin", "notlogin");
                Intent Homedashboard = new Intent(ProfilePreviewActivity.this, HomeActivity.class);
                Homedashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(Homedashboard);


            }
        });


        linearLayoutResetpassword.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {

                Intent change_password = new Intent(ProfilePreviewActivity.this, ChangePassword.class);
                startActivity(change_password);

            }
        });


        linearLayoutAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent address = new Intent(ProfilePreviewActivity.this, AddAddressActivity.class);
                startActivity(address);

            }
        });

    }


    private void setuptoolbar()
    {

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText("My Profile");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Profile");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
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


    public void save_shared_pref(String user_id, String user_name, String email_id)
    {
        app_sharedpreference.setsharedpref("userid", user_id);
        app_sharedpreference.setsharedpref("username", user_name);
        app_sharedpreference.setsharedpref("emailid", email_id);

    }
}