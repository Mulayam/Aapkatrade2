package com.example.pat.aapkatrade.user_dashboard.my_profile;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.pat.aapkatrade.R;

import com.badoualy.stepperindicator.StepperIndicator;
import com.example.pat.aapkatrade.categories_tab.BlankFragment;
import com.example.pat.aapkatrade.general.App_sharedpreference;
import com.example.pat.aapkatrade.user_dashboard.order_list.OrderActivity;
import com.example.pat.aapkatrade.user_dashboard.order_list.OrderManagementActivity;
import com.example.pat.aapkatrade.user_dashboard.order_list.cancel_order_fragment.CancelOrderFragment;
import com.example.pat.aapkatrade.user_dashboard.order_list.complete_order.CompleteOrderFragment;
import com.example.pat.aapkatrade.user_dashboard.order_list.shipped_fragment.ShippedFragment;

public class MyProfileForBusinessAssociates extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private int[] tabIcons = {

            R.drawable.new_order_grn,
            R.drawable.new_order_wht,

            R.drawable.cancel_order_grn,
            R.drawable.cancel_order_wht,

            R.drawable.shipped_order_grn,
            R.drawable.shipped_order_wht,

    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_for_business_associates);
        setuptoolbar();
        viewPager = (ViewPager) findViewById(R.id.htab_viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(this);


        tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#ffffff"));

        setupTabIcons();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[1]);
        tabLayout.getTabAt(1).setIcon(tabIcons[2]);
        tabLayout.getTabAt(2).setIcon(tabIcons[4]);
    }

    private void setupViewPager(ViewPager viewPager) {
        MyProfilePagerAdapter adapter = new MyProfilePagerAdapter(getSupportFragmentManager());
        adapter.addFrag(MyProfileFragment.newInstance(0, false), getResources().getString(R.string.personalInfo));
        adapter.addFrag(MyProfileFragment.newInstance(1, false), getResources().getString(R.string.professionalDetails));
        adapter.addFrag(MyProfileFragment.newInstance(2, true), getResources().getString(R.string.bank_details));
        viewPager.setAdapter(adapter);
    }


    private void setuptoolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setElevation(0);
        }
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


    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
