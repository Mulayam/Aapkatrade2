package com.example.pat.aapkatrade.user_dashboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.pat.aapkatrade.R;
import java.util.ArrayList;

public class DashboardFragment extends Fragment
{


    RecyclerView dashboardlist;
    DashboardAdapter dashboardAdapter;
    ArrayList<DashboardData> dashboardDatas = new ArrayList<>();



    public DashboardFragment()
    {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View v = inflater.inflate(R.layout.activity_dashboard, container, false);

        setup_data();

        dashboardlist = (RecyclerView) v.findViewById(R.id.dashboardlist);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),3);
        dashboardlist.setLayoutManager(layoutManager);

        dashboardAdapter = new DashboardAdapter(getActivity(), dashboardDatas);
        dashboardlist.setAdapter(dashboardAdapter);


        return v;
    }



    private void setup_data()
    {
        dashboardDatas.clear();
        try
        {

            dashboardDatas.add(new DashboardData("","My Company",R.drawable.ic_companyprofile));
            dashboardDatas.add(new DashboardData("","My Profile",R.drawable.ic_myprofile));
            dashboardDatas.add(new DashboardData("","Change Password",R.drawable.ic_chngpswd));
            dashboardDatas.add(new DashboardData("","Add Company",R.drawable.ic_add_company));
            dashboardDatas.add(new DashboardData("","Company List",R.drawable.ic_lstcmpny));
            dashboardDatas.add(new DashboardData("","Add Product",R.drawable.ic_adprdct));
            dashboardDatas.add(new DashboardData("","List Product",R.drawable.ic_lstprdct));
            dashboardDatas.add(new DashboardData("","Order",R.drawable.ic_lstprdct));

        }
        catch (Exception  e)
        {

        }
    }




}
