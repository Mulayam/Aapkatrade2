package com.example.pat.aapkatrade.user_dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pat.aapkatrade.Home.registration.RegistrationActivity;
import com.example.pat.aapkatrade.Home.registration.RegistrationBusinessAssociateActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.App_sharedpreference;
import com.example.pat.aapkatrade.login.LoginActivity;

import java.util.ArrayList;

public class User_DashboardFragment extends Fragment {


    RecyclerView dashboardlist;
    DashboardAdapter dashboardAdapter;
    ArrayList<DashboardData> dashboardDatas = new ArrayList<>();
    App_sharedpreference app_sharedpreference;

    public User_DashboardFragment() {



    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_dashboard, container, false);

        app_sharedpreference = new App_sharedpreference(getActivity());
        setup_data();

        dashboardlist = (RecyclerView) v.findViewById(R.id.dashboardlist);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        dashboardlist.setLayoutManager(layoutManager);

        dashboardAdapter = new DashboardAdapter(getContext(), dashboardDatas);
        dashboardlist.setAdapter(dashboardAdapter);

        dashboardlist.setNestedScrollingEnabled(false);

        return v;
    }


    private void setup_data() {
        dashboardDatas.clear();
        try {

            if (app_sharedpreference.shared_pref != null) {
                if (app_sharedpreference.getsharedpref("usertype", "0").equals("3")) {
                    dashboardDatas.add(new DashboardData("", "My Profile", R.drawable.ic_myprofile, R.drawable.circle_teal, false));
                    dashboardDatas.add(new DashboardData("", "Change Password", R.drawable.ic_chngpswd, R.drawable.circle_purple, false));
                    dashboardDatas.add(new DashboardData("", "Add Vendor", R.drawable.ic_companyprofile, R.drawable.circle_voilet, false));
                    dashboardDatas.add(new DashboardData("", "Vendor List", R.drawable.ic_add_company, R.drawable.circle_deep_pink, false));
                } else if ((app_sharedpreference.getsharedpref("usertype", "0").equals("2"))) {
                    dashboardDatas.add(new DashboardData("", "My Profile", R.drawable.ic_myprofile, R.drawable.circle_teal, false));
                    dashboardDatas.add(new DashboardData("", "Change Password", R.drawable.ic_chngpswd, R.drawable.circle_purple, false));
                    dashboardDatas.add(new DashboardData("", "Order", R.drawable.ic_lstprdct, R.drawable.circle_sienna, true));
                    dashboardDatas.add(new DashboardData("", "Cancel Order", R.drawable.ic_lstprdct, R.drawable.circle_cherry_red, true));
                } else if (app_sharedpreference.getsharedpref("usertype", "0").equals("1")) {
                    dashboardDatas.add(new DashboardData("", "My Profile", R.drawable.ic_myprofile, R.drawable.circle_teal, false));
                    dashboardDatas.add(new DashboardData("", "Change Password", R.drawable.ic_chngpswd, R.drawable.circle_purple, false));
                    dashboardDatas.add(new DashboardData("", "Add Company", R.drawable.ic_add_company, R.drawable.circle_cherry_red, false));
                    dashboardDatas.add(new DashboardData("", "Company List", R.drawable.ic_lstcmpny, R.drawable.circle_deep_pink, true));
                    dashboardDatas.add(new DashboardData("", "Add Product", R.drawable.ic_adprdct, R.drawable.circle_turquoise, false));
                    dashboardDatas.add(new DashboardData("", "List Product", R.drawable.ic_lstprdct, R.drawable.circle_slate_gray, true));
                    dashboardDatas.add(new DashboardData("", "Order", R.drawable.ic_lstprdct, R.drawable.circle_sienna, true));
                }
            } else {
                Log.e("null_sharedPreferences", "sharedPreferences");
            }

        } catch (Exception e) {
        }
      
    public void setup_buyer_data()
    {
        dashboardDatas.clear();
        try
        {
            dashboardDatas.add(new DashboardData("", "My Profile", R.drawable.ic_myprofile,R.drawable.circle_teal));
            dashboardDatas.add(new DashboardData("", "Change Password", R.drawable.ic_chngpswd,R.drawable.circle_purple));
            dashboardDatas.add(new DashboardData("", "Order", R.drawable.ic_add_company,R.drawable.circle_cherry_red));
            dashboardDatas.add(new DashboardData("", "Cancel Order", R.drawable.ic_lstcmpny,R.drawable.circle_deep_pink));

        }
        catch (Exception bussiness_associate)
        {

        }

    }


    public void setup_bussiness_associate()
    {
        dashboardDatas.clear();
        try
        {

            dashboardDatas.add(new DashboardData("", "My Profile", R.drawable.ic_myprofile,R.drawable.circle_teal));
            dashboardDatas.add(new DashboardData("", "Change Password", R.drawable.ic_chngpswd,R.drawable.circle_purple));
            dashboardDatas.add(new DashboardData("", "Add Vender", R.drawable.ic_add_company,R.drawable.circle_cherry_red));
            dashboardDatas.add(new DashboardData("", "Vender List", R.drawable.ic_lstcmpny,R.drawable.circle_deep_pink));

        }
        catch (Exception bussiness_associate)
        {

        }

    }

}
