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
import android.widget.Button;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.navigation.NavigationFragment;
import com.example.pat.aapkatrade.Home.registration.RegistrationActivity;
import com.example.pat.aapkatrade.Home.registration.RegistrationBusinessAssociateActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.App_sharedpreference;

import com.example.pat.aapkatrade.user_dashboard.my_profile.MyProfileActivity;

import java.util.ArrayList;

public class User_DashboardFragment extends Fragment
{

    RecyclerView dashboardlist;
    DashboardAdapter dashboardAdapter;
    ArrayList<DashboardData> dashboardDatas = new ArrayList<>();
    App_sharedpreference app_sharedpreference;
    TextView tvMobile, tvEmail, textViewName, tvUserType;
    Button btnEdit;


    public User_DashboardFragment()
    {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View v = inflater.inflate(R.layout.activity_dashboard, container, false);

        app_sharedpreference = new App_sharedpreference(getActivity());

        tvUserType = (TextView) v.findViewById(R.id.tvUserType);

        setup_data();

        setup_layout(v);

        dashboardlist = (RecyclerView) v.findViewById(R.id.dashboardlist);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        dashboardlist.setLayoutManager(layoutManager);

        dashboardAdapter = new DashboardAdapter(getContext(), dashboardDatas);
        dashboardlist.setAdapter(dashboardAdapter);

        dashboardlist.setNestedScrollingEnabled(false);

        return v;
    }

    private void setup_layout(View v)
    {
        textViewName = (TextView) v.findViewById(R.id.textViewName);

        tvMobile = (TextView) v.findViewById(R.id.tvMobile);

        tvEmail = (TextView) v.findViewById(R.id.tvEmail);

        btnEdit = (Button) v.findViewById(R.id.btnEdit);

        btnEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent i = new Intent(getActivity(), MyProfileActivity.class);
                startActivity(i);


            }
        });

        if (app_sharedpreference.getsharedpref("username", "notlogin") != null)
        {
            String Username = app_sharedpreference.getsharedpref("username", "not");
            String Emailid = app_sharedpreference.getsharedpref("emailid", "not");

            if (Username.equals("notlogin"))
            {

                textViewName.setText("");
                tvEmail.setText("");

            }
            else
            {
                textViewName.setText(Username);
                tvEmail.setText(Emailid);

            }
        }


    }


    private void setup_data() {
        dashboardDatas.clear();
        try {

            if (app_sharedpreference.shared_pref != null) {
                if (app_sharedpreference.getsharedpref("usertype", "0").equals("3")) {





             String vendor_quantity= app_sharedpreference.getsharedpref("vendor_quantity","0");



                      Log.e("vendor_quantity",vendor_quantity);


                    dashboardDatas.add(new DashboardData("", "My Profile", R.drawable.ic_myprofile, R.drawable.circle_teal, false,""));
                    dashboardDatas.add(new DashboardData("", "Change Password", R.drawable.ic_chngpswd, R.drawable.circle_purple, false,""));
                    dashboardDatas.add(new DashboardData("", "Add Vendor", R.drawable.ic_companyprofile, R.drawable.circle_voilet, false,""));
                    dashboardDatas.add(new DashboardData("", "Vendor List", R.drawable.ic_add_company, R.drawable.circle_deep_pink, true,vendor_quantity));

                    tvUserType.setText("Welcome Bussiness Associate");


                } else if ((app_sharedpreference.getsharedpref("usertype", "0").equals("2"))) {
                  String order_quantity=  app_sharedpreference.getsharedpref("order_quantity","0");
                    Log.e("order_quantity",order_quantity);
                    dashboardDatas.add(new DashboardData("", "My Profile", R.drawable.ic_myprofile, R.drawable.circle_teal, false,""));
                    dashboardDatas.add(new DashboardData("", "Change Password", R.drawable.ic_chngpswd, R.drawable.circle_purple, false,""));
                    dashboardDatas.add(new DashboardData("", "Order", R.drawable.ic_lstprdct, R.drawable.circle_sienna, true,order_quantity));
                    dashboardDatas.add(new DashboardData("", "Cancel Order", R.drawable.ic_lstprdct, R.drawable.circle_cherry_red, true,""));

                    tvUserType.setText("Welcome Buyer");


                } else if (app_sharedpreference.getsharedpref("usertype", "0").equals("1")) {



                  String order_quantity=   app_sharedpreference.getsharedpref("order_quantity","0");

                    String product_quantity= app_sharedpreference.getsharedpref("product_quantity","0");
                    String company_quantity=  app_sharedpreference.getsharedpref("company_quantity","0");

                    Log.e("order_quantity",order_quantity);
                    Log.e("product_quantity",product_quantity);
                    Log.e("company_quantity",company_quantity);
                    dashboardDatas.add(new DashboardData("", "My Profile", R.drawable.ic_myprofile, R.drawable.circle_teal, false,""));
                    dashboardDatas.add(new DashboardData("", "Change Password", R.drawable.ic_chngpswd, R.drawable.circle_purple, false,""));
                    dashboardDatas.add(new DashboardData("", "Add Company", R.drawable.ic_add_company, R.drawable.circle_cherry_red, false,""));
                    dashboardDatas.add(new DashboardData("", "Company List", R.drawable.ic_lstcmpny, R.drawable.circle_deep_pink, true,company_quantity));
                    dashboardDatas.add(new DashboardData("", "Add Product", R.drawable.ic_adprdct, R.drawable.circle_turquoise, false,""));
                    dashboardDatas.add(new DashboardData("", "List Product", R.drawable.ic_lstprdct, R.drawable.circle_slate_gray, true,product_quantity));
                    dashboardDatas.add(new DashboardData("", "Order", R.drawable.ic_lstprdct, R.drawable.circle_sienna, true,order_quantity));


                    String Username = app_sharedpreference.getsharedpref("username", "not");

                    if (Username.toString().equals("notlogin")) {
                        tvUserType.setText("Welcome Guest");
                        NavigationFragment.usertype.setText("Welcome Guest");

                    } else {
                        tvUserType.setText("Welcome Seller");

                    }

                }
            } else {
                Log.e("null_sharedPreferences", "sharedPreferences");
            }

        } catch (Exception e) {
        }


    }


}
