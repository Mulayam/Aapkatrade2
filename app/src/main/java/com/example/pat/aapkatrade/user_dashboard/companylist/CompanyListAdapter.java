package com.example.pat.aapkatrade.user_dashboard.companylist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pat.aapkatrade.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by John on 10/31/2016.
 */
public class CompanyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

     final LayoutInflater inflater;
     List<CompanyData> itemList;
     Context context;
     CompanyListHolder viewHolder;
     CompanyList companylist;


    public CompanyListAdapter(Context context, List<CompanyData> itemList,CompanyList companylist)
    {
        this.companylist = companylist;
        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = inflater.inflate(R.layout.row_company_list, parent, false);
        viewHolder = new CompanyListHolder(view);


        System.out.println("data-----------"+itemList);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {


        CompanyListHolder homeHolder = (CompanyListHolder) holder;

        Log.e("data===========arvin",itemList.get(position).company_name.toString());

        homeHolder.tvCompanyname.setText(itemList.get(position).company_name.toString());

        homeHolder.tvDate.setText(itemList.get(position).company_creation_date.toString());


        homeHolder.imgDeleteCompany.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String company_id = itemList.get(position).company_id.toString();
                notifyItemRemoved(position);
                notifyDataSetChanged();
               // delete_company(company_id);

            }
        });

        homeHolder.imgEdtCompanyName.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {



            }
        });




    }

    private void delete_company(String company_id)
    {

            Ion.with(context)
                    .load("http://aapkatrade.com/slim/listCompany")
                    .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                    .setBodyParameter("type", "company")
                    .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                    .setBodyParameter("id", company_id)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>()
                    {
                        @Override
                        public void onCompleted(Exception e, JsonObject result)
                        {



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
        // return itemList.size();
    }

    public String getCurrentTimeStamp()
    {
        return new SimpleDateFormat("dd MMM yyyy HH:mm").format(new Date());
    }
}