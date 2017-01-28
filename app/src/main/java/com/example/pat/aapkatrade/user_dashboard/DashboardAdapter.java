package com.example.pat.aapkatrade.user_dashboard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.user_dashboard.add_product.AddProductActivity;
import com.example.pat.aapkatrade.user_dashboard.addcompany.AddCompany;
import com.example.pat.aapkatrade.user_dashboard.changepassword.ChangePassword;
import com.example.pat.aapkatrade.user_dashboard.companylist.CompanyList;
import com.example.pat.aapkatrade.user_dashboard.my_company_profile.MyCompanyProfile;
import com.example.pat.aapkatrade.user_dashboard.my_profile.MyProfileActivity;
import com.example.pat.aapkatrade.user_dashboard.order_list.OrderActivity;
import com.example.pat.aapkatrade.user_dashboard.product_list.ProductListActivity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by PPC16 on 10-Jan-17.
 */

public class DashboardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private final LayoutInflater inflater;
    private List<DashboardData> itemList;
    private Context context;
    DashboardHolder viewHolder;



    public DashboardAdapter(Context context, List<DashboardData> itemList)
    {
        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = inflater.inflate(R.layout.row_dashboard2, parent, false);
        viewHolder = new DashboardHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {

        DashboardHolder homeHolder = (DashboardHolder) holder;

        homeHolder.tvDashboard.setText(itemList.get(position).dashboard_name.toString());

        Picasso.with(context).load(itemList.get(position).image).into(homeHolder.imageView);

        ((DashboardHolder) holder).imageView.setBackground(context.getResources().getDrawable(itemList.get(position).color));



        homeHolder.relativeDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                 if(itemList.get(position).dashboard_name.equals("My Company"))
                {

                    Intent my_company = new Intent(context, MyCompanyProfile.class);
                    context.startActivity(my_company);

                }
                else if (itemList.get(position).dashboard_name.equals("My Profile"))
                {
                    Intent my_profile = new Intent(context, MyProfileActivity.class);
                    context.startActivity(my_profile);

                }
                else if (itemList.get(position).dashboard_name.equals("Change Password"))
                {

                    Intent change_password = new Intent(context, ChangePassword.class);
                    context.startActivity(change_password);

                }
                else if (itemList.get(position).dashboard_name.equals("Add Company"))
                {

                    Intent add_company = new Intent(context, AddCompany.class);
                    context.startActivity(add_company);

                }
                else if (itemList.get(position).dashboard_name.equals("Company List"))
                {

                    Intent list_company = new Intent(context, CompanyList.class);
                    context.startActivity(list_company);

                }
                else if (itemList.get(position).dashboard_name.equals("Add Product"))
                {

                    Intent add_product = new Intent(context, AddProductActivity.class);
                    context.startActivity(add_product);

                }
                else if (itemList.get(position).dashboard_name.equals("List Product"))
                {

                   Intent list_product = new Intent(context, ProductListActivity.class);
                    context.startActivity(list_product);

                }
                 else if (itemList.get(position).dashboard_name.equals("Order"))
                 {

                     Intent list_product = new Intent(context, OrderActivity.class);
                     context.startActivity(list_product);

                 }


            }
        });

    }

    private void showMessage(String s)
    {

        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getItemCount()
    {
        return itemList.size();

    }

    public String getCurrentTimeStamp()
    {
        return new SimpleDateFormat("dd MMM yyyy HH:mm").format(new Date());
    }
}
