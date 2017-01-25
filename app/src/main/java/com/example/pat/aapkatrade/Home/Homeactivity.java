package com.example.pat.aapkatrade.Home;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.pat.aapkatrade.Home.navigation.NavigationFragment;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.App_config;
import com.example.pat.aapkatrade.general.CheckPermission;
import com.example.pat.aapkatrade.login.LoginDashboard;
import com.example.pat.aapkatrade.user_dashboard.User_DashboardFragment;
import com.example.pat.aapkatrade.user_dashboard.my_profile.MyProfileActivity;


public class HomeActivity extends AppCompatActivity
{
    private NavigationFragment drawer;
    private Toolbar toolbar;
    private com.example.pat.aapkatrade.Home.DashboardFragment homeFragment;

    Context context;
    public  static  String shared_pref_name="aapkatrade";
    App_config aa;
    AHBottomNavigation bottomNavigation;
    CoordinatorLayout coordinatorLayout;
    User_DashboardFragment user_dashboardFragment;
    public static String  userid,username;
    ScrollView scrollView;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        App_config.set_defaultfont(HomeActivity.this);
       loadLocale();

        setContentView(R.layout.activity_homeactivity);
prefs = getSharedPreferences(shared_pref_name, Activity.MODE_PRIVATE);



        context = this;
        setup_bottomNavigation();
        if (CheckPermission.checkPermissions(HomeActivity.this))
        //  permissions  granted.

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
                if(prefs.getString("userid","notlogin").equals("notlogin"))
                {
                    Intent i =new Intent(HomeActivity.this, LoginDashboard.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                    return true;
                }
                else{
                    Intent i =new Intent(HomeActivity.this, MyProfileActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                    return true;


                }

                //finish();


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

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();
        Log.e("language_pref",langPref+"****"+lang);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CheckPermission.MULTIPLE_PERMISSIONS:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Log.e("permission_granted","permission_granted");
                    // permissions granted.
                } else {
                    Log.e("permission_requried","permission_requried");
                    // no permissions granted.
                }
                return;
            }
        }
    }


    private void setup_bottomNavigation()
    {
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordination_home_activity);

        bottomNavigation = (AHBottomNavigation)findViewById(R.id.bottom_navigation);
        scrollView=(ScrollView)findViewById(R.id.scroll_main);
        setup_scrollview(scrollView);

//        tabColors = getActivity().getResources().getIntArray(R.array.tab_colors);
//        bottom_menuAdapter = new AHBottomNavigationAdapter(getActivity(), R.menu.button_menu);
//        bottom_menuAdapter.setupWithBottomNavigation(bottomNavigation, tabColors);

// Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.ic_navigation_home, R.color.dark_green);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.ic_home_dashboard_aboutus, R.color.orange);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.ic_home_dashboard_rate_us, R.color.dark_green);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.tab_4, R.drawable.ic_home_bottom_account, R.color.dark_green);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem(R.string.tab_5, R.drawable.ic_about_us, R.color.dark_green);

// Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item5);

// Set background color
        bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.dark_green));

// Disable the translation inside the CoordinatorLayout
        bottomNavigation.setBehaviorTranslationEnabled(true);
        bottomNavigation.setSelectedBackgroundVisible(true);


// Enable the translation of the FloatingActionButton
        //  bottomNavigation.manageFloatingActionButtonBehavior(floatingActionButton);

// Change colors
        bottomNavigation.setAccentColor(Color.parseColor("#FEFEFE"));
        bottomNavigation.setInactiveColor(Color.parseColor("#000000"));

// Force to tint the drawable (useful for font with icon for example)
        bottomNavigation.setForceTint(true);

// Display color under navigation bar (API 21+)
// Don't forget these lines in your style-v21
// <item name="android:windowTranslucentNavigation">true</item>
// <item name="android:fitsSystemWindows">true</item>
        bottomNavigation.setTranslucentNavigationEnabled(true);

// Manage titles
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

// Use colored navigation with circle reveal effect
        bottomNavigation.setColored(false);

// Set current item programmatically
        bottomNavigation.setCurrentItem(0);

// Customize notification (title, background, typeface)
//       bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F63D2B"));
//
//// Add or remove notification for each item
//        bottomNavigation.setNotification("", 3);
// OR
//        AHNotification notification = new AHNotification.Builder()
//                .setText("1")
//                .setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.dark_green))
//                .setTextColor(ContextCompat.getColor(getActivity(), R.color.grey))
//                .build();
//        bottomNavigation.setNotification(notification, 1);

// Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {



                switch (position)
                {case 0:
                    if (homeFragment == null)
                    {
                        homeFragment = new com.example.pat.aapkatrade.Home.DashboardFragment();
                    }
                    String tagName = homeFragment.getClass().getName();
                    replaceFragment(homeFragment, tagName);
                    break;



                    case 3:
                        if (user_dashboardFragment == null)
                        {
                            user_dashboardFragment = new User_DashboardFragment();
                        }
                        //String tagName_dashboardFragment = User_DashboardFragment.getClass().getName();
                        replaceFragment(user_dashboardFragment,"");
                        break;
                }
                // Do something cool here...
                return true;
            }
        });
        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override public void onPositionChange(int y) {
                // Manage the new y position
            }
        });

    }



    @TargetApi(Build.VERSION_CODES.M)
    private void setup_scrollview(final ScrollView scrollView) {
        if (Build.VERSION.SDK_INT >= 23) {
            // Marshmallow+


            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    int pos= scrollView.getChildCount()-1;
                    if(oldScrollY<scrollY)
                    {showOrHideBottomNavigation(true);
//                    setForceTitleHide(true);

                    }

                    else{
                        showOrHideBottomNavigation(false);
                    }

                    if(oldScrollY==scrollY)
                    {
                        showOrHideBottomNavigation(true);

                    }



                }
            });
        }

        else {
            // Pre-Marshmallow
        }



    }

    private void setForceTitleHide(boolean forceTitleHide) {


        AHBottomNavigation.TitleState state = forceTitleHide ? AHBottomNavigation.TitleState.ALWAYS_HIDE : AHBottomNavigation.TitleState.ALWAYS_SHOW;
        bottomNavigation.setTitleState(state);
    }







    public void showOrHideBottomNavigation(boolean show) {
        if (show) {
            bottomNavigation.restoreBottomNavigation(true);
        } else {
            bottomNavigation.hideBottomNavigation(true);
        }
    }


}


