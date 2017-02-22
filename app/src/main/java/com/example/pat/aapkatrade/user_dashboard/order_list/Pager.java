package com.example.pat.aapkatrade.user_dashboard.order_list;

/**
 * Created by PPC16 on 22-Feb-17.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.example.pat.aapkatrade.categories_tab.BlankFragment;
import com.example.pat.aapkatrade.user_dashboard.order_list.cancel_order_fragment.CancelOrderFragment;
import com.example.pat.aapkatrade.user_dashboard.order_list.shipped_fragment.ShippedFragment;

/**
 * Created by Belal on 2/3/2016.
 */
//Extending FragmentStatePagerAdapter
public class Pager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public Pager(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                BlankFragment tab1 = new BlankFragment();
                return tab1;
            case 1:
                CancelOrderFragment tab2 = new CancelOrderFragment();
                return tab2;
            case 2:
                ShippedFragment tab3 = new ShippedFragment();
                return tab3;
            case 3:
                ShippedFragment tab4 = new ShippedFragment();
                return tab4;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}