package com.example.pat.aapkatrade.user_dashboard.my_profile;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

/**
 * Created by PPC09 on 03-Mar-17.
 */


public class MyProfilePagerAdapter extends FragmentPagerAdapter {

    public MyProfilePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int position) {
        Log.e("page", "pagee123"+position);
        return MyProfileFragment.newInstance(position, position == getCount());
    }

}