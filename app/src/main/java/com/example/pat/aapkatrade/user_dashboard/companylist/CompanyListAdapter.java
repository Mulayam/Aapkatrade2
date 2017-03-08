package com.example.pat.aapkatrade.user_dashboard.companylist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.App_sharedpreference;
import com.example.pat.aapkatrade.user_dashboard.companylist.compant_details.CompanyDetailActivity;
import com.example.pat.aapkatrade.user_dashboard.editcompany.EditCompanyActivity;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.text.ParseException;
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
     Boolean showBoolean = false;
     int clickedposition=1000;
     int p;
     App_sharedpreference app_sharedpreference;
     String email;




    public CompanyListAdapter(Context context, List<CompanyData> itemList,CompanyList companylist)
    {
        this.companylist = companylist;
        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);

        app_sharedpreference = new App_sharedpreference(context);



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

        final CompanyListHolder homeHolder = (CompanyListHolder) holder;

        Log.e("data===========arvin",itemList.get(position).company_name);

        homeHolder.tvCompanyname.setText(itemList.get(position).company_name);

        email = app_sharedpreference.getsharedpref("emailid", "");

        homeHolder.tvEmail.setText(email);

        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = null;
        try
        {
            date = form.parse(itemList.get(position).company_creation_date);

            System.out.println("datae----------"+itemList.get(position).company_creation_date);

        }
        catch (ParseException e)
        {

            e.printStackTrace();
        }
        SimpleDateFormat postFormater = new SimpleDateFormat("MMMM dd, yyyy");
        String newDateStr = postFormater.format(date);

        homeHolder.tvDate.setText(newDateStr);


        homeHolder.imgNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                itemList.get(position).clicked=true;
                if( itemList.get(position).clicked== false)
                {
                    clickedposition = position;
                    itemList.get(clickedposition).clicked=false;
                    notifyDataSetChanged();
                    homeHolder.linearLayoutDetail.setVisibility(View.GONE);

                    homeHolder.View1.setVisibility(View.GONE);
                    homeHolder.imgNext.setImageResource(R.drawable.ic_red_arw);


                    Log.e("working1","working1");

                }
                else {
                    Log.e("working2","working2");



                    if (showBoolean) {
                        homeHolder.linearLayoutDetail.setVisibility(View.GONE);
                        homeHolder.View1.setVisibility(View.GONE);
                        homeHolder.imgNext.setImageResource(R.drawable.ic_red_arw);
                        showBoolean = false;
                    } else {
                        showBoolean = true;
                        homeHolder.linearLayoutDetail.setVisibility(View.VISIBLE);
                        homeHolder.imgNext.setImageResource(R.drawable.ic_arw_down);
                        homeHolder.View1.setVisibility(View.VISIBLE);

                    }



                itemList.get(position).clicked=true;
                    notifyDataSetChanged();
                }

                // check if item still exists
//                if(position != RecyclerView.NO_POSITION){
//                    homeHolder.linearLayoutDetail.setVisibility(View.GONE);
//                    homeHolder.View1.setVisibility(View.GONE);
//                    homeHolder.imgNext.setImageResource(R.drawable.ic_red_arw);
//                }
//                else{
//
//                    homeHolder.linearLayoutDetail.setVisibility(View.VISIBLE);
//                    homeHolder.imgNext.setImageResource(R.drawable.ic_arw_down);
//                    homeHolder.View1.setVisibility(View.VISIBLE);
//
//
//                }




            }
        });

        homeHolder.relativecompanyList.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

             /*   Intent i = new Intent(context, CompanyDetailActivity.class);
                context.startActivity(i);
            */

            }
        });

        homeHolder.imgEdtCompanyName.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                 Intent editCompany = new Intent(context, EditCompanyActivity.class);
                 editCompany.putExtra("company_name",itemList.get(position).company_name);
                 editCompany.putExtra("company_creation_date",itemList.get(position).company_creation_date);
                 editCompany.putExtra("address",itemList.get(position).address);
                 editCompany.putExtra("description",itemList.get(position).description);
                 context.startActivity(editCompany);


            }
        });

        homeHolder.imgDeleteCompany.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                Toast.makeText(context, "hi dear", Toast.LENGTH_SHORT).show();

                System.out.println("deiete company--------"+itemList.get(position).company_id);

                delete_company(itemList.get(position).company_id, position);



            }

        });

    }

    private void delete_company(String company, int pos)
    {
        p = pos;
        System.out.println(" company--------"+company);
            Ion.with(context)
                    .load("https://aapkatrade.com/slim/delete_company")
                    .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                    .setBodyParameter("company_id",company)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>()
                    {
                        @Override
                        public void onCompleted(Exception e, JsonObject result)
                        {

                            if (result == null)
                            {



                            }
                            else
                            {
                                JsonObject jsonObject = result.getAsJsonObject();
                                String message = jsonObject.get("message").getAsString();
                                if (message.equals("Success"))
                                {
                                    itemList.remove(p);
                                    notifyItemRemoved(p);
                                    notifyItemRangeChanged(p, itemList.size());
                                }
                                else
                                {
                                    Toast.makeText(context,message.toString(),Toast.LENGTH_SHORT).show();
                                }
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
        //return itemList.size();

    }

    public String getCurrentTimeStamp()
    {
        return new SimpleDateFormat("dd MMM yyyy HH:mm").format(new Date());
    }



}