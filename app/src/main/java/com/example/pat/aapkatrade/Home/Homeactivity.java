package com.example.pat.aapkatrade.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.pat.aapkatrade.Home.navigation.NavigationFragment;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.App_config;
import com.example.pat.aapkatrade.login.logindashboard;
import com.example.pat.aapkatrade.user_dashboard.DashboardFragment;

public class Homeactivity extends AppCompatActivity
{


    private NavigationFragment drawer;
    private Toolbar toolbar;
    private com.example.pat.aapkatrade.Home.DashboardFragment homeFragment;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        App_config.setLocaleFa(Homeactivity.this);
        setContentView(R.layout.activity_homeactivity);
        context = this;

        setupToolBar();
        //setupNavigation();
        setupNavigationCustom();
        setupDashFragment();
        Intent iin= getIntent();
        Bundle b = iin.getExtras();



//        if(b!=null)
//        {
//            username =(String) b.get("username");
//            mobno =(String) b.get("mobno");
//            email =(String) b.get("email");
//            Lastname=(String) b.get("lname");
//            dob=(String) b.get("dob");
//
//            drawer.setdata(username,mobno,email,Lastname,dob);
//
//
//
//        }




    }


    private void setupNavigationCustom()
    {
        drawer = (NavigationFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        drawer.setup(R.id.fragment, (DrawerLayout) findViewById(R.id.drawer), toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        // ActionItemBadge.update(((AppCompatActivity) context), menu.findItem(R.id.login), ContextCompat.getDrawable(context, R.drawable.ic_cart_black)
        // , ActionItemBadge.BadgeStyles.GREY, 3);
        return true;
    }


    private void setupToolBar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(null);

        getSupportActionBar().setIcon(R.drawable.home_logo);
    }

    private void replaceFragment(Fragment newFragment, String tag)
    {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.drawer_layout, newFragment, tag).addToBackStack(tag);
        transaction.commit();
    }

    private void setupDashFragment()
    {
        if (homeFragment == null)
        {
            homeFragment = new com.example.pat.aapkatrade.Home.DashboardFragment();
        }
        String tagName = homeFragment.getClass().getName();
        replaceFragment(homeFragment, tagName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle item selection
        switch (item.getItemId())
        {
            case R.id.login:
                //finish();
                Intent i =new Intent(Homeactivity.this, logindashboard.class);
                startActivity(i);
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                return true;

           case R.id.language:

               DashboardFragment dashboardFragment = new DashboardFragment();
               FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
               transaction.replace(R.id.drawer_layout, dashboardFragment, null).addToBackStack(null);
               transaction.commit();

               return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed()
    {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1)
        {
            finish();
        }
        else
        {
            super.onBackPressed();
        }


    }



}


