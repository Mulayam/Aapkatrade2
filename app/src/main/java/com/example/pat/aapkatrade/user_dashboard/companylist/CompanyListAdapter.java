package com.example.pat.aapkatrade.user_dashboard.companylist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pat.aapkatrade.R;
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
public class CompanyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final LayoutInflater inflater;
    List<CompanyData> itemList;
    Context context;
    CompanyListHolder viewHolder;
    CompanyList companylist;
    Boolean showBoolean = false;


    public CompanyListAdapter(Context context, List<CompanyData> itemList, CompanyList companylist) {
        this.companylist = companylist;
        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_company_list, parent, false);

        viewHolder = new CompanyListHolder(view);

        System.out.println("data-----------" + itemList);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final CompanyListHolder homeHolder = (CompanyListHolder) holder;

        Log.e("data===========arvin", itemList.get(position).company_name);

        homeHolder.tvCompanyname.setText(itemList.get(position).company_name);

        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = null;
        try {
            date = form.parse(itemList.get(position).company_creation_date);

            System.out.println("datae----------" + itemList.get(position).company_creation_date);

        } catch (ParseException e) {

            e.printStackTrace();
        }
        SimpleDateFormat postFormater = new SimpleDateFormat("MMMM dd, yyyy");
        String newDateStr = postFormater.format(date);

        homeHolder.tvDate.setText(newDateStr);


        homeHolder.imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

            }
        });

        homeHolder.relativecompanyList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             /*   Intent i = new Intent(context, CompanyDetailActivity.class);
                context.startActivity(i);
            */

            }
        });


    }

    private void delete_company(String company_id) {
        Ion.with(context)
                .load("http://aapkatrade.com/slim/listCompany")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "company")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("id", company_id)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                    }

                });
    }


    private void showMessage(String s) {

        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
        //return itemList.size();
    }

    public String getCurrentTimeStamp() {
        return new SimpleDateFormat("dd MMM yyyy HH:mm").format(new Date());
    }


}