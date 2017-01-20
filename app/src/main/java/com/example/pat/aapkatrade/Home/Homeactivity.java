package com.example.pat.aapkatrade.Home;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.pat.aapkatrade.Home.navigation.NavigationFragment;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.App_config;


import com.example.pat.aapkatrade.login.LoginDashboard;






public class HomeActivity extends AppCompatActivity
{
    private NavigationFragment drawer;
    private Toolbar toolbar;
    private com.example.pat.aapkatrade.Home.DashboardFragment homeFragment;
    Context context;
    public  static  String shared_pref_name="aapkatrade";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


       loadLocale();

        setContentView(R.layout.activity_homeactivity);
        context = this;

        setupToolBar();
        //setupNavigation();
        setupNavigationCustom();
        setupDashFragment();
        Intent iin= getIntent();
        Bundle b = iin.getExtras();

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
        transaction.replace(R.id.drawer_layout, newFragment, tag).addToBackStack(null);
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
                Intent i =new Intent(HomeActivity.this, LoginDashboard.class);
                startActivity(i);
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                return true;

           case R.id.language:
               View menuItemView = findViewById(R.id.language);
               PopupMenu popup = new PopupMenu(HomeActivity.this.context, menuItemView);
               //Inflating the Popup using xml file
               popup.getMenuInflater().inflate(R.menu.menu_language_list, popup.getMenu());


               //registering popup with OnMenuItemClickListener
               popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                   public boolean onMenuItemClick(MenuItem item) {
                       switch (item.getItemId())
                       {
                           case R.id.english_language:
                               saveLocale("en");


                              // getIntent().start
                               return true;

                           case R.id.hindi_language:
                               saveLocale("hi");

                               return true;
                       }


                       return true;
                   }
               });

               popup.show();//showing popup menu




//               User_DashboardFragment dashboardFragment = new User_DashboardFragment();
//               FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//               transaction.replace(R.id.drawer_layout, dashboardFragment, null).addToBackStack(null);
//               transaction.commit();

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


    public void loadLocale() {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences(shared_pref_name,
                Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
        App_config.setLocaleFa(HomeActivity.this,language);
       Log.e("language",language);
       // changeLang(language);
    }

    public void saveLocale(String lang)
    {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences(shared_pref_name, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();
        Log.e("language_pref",langPref+"****"+lang);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }





}


