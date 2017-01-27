package com.example.pat.aapkatrade.user_dashboard.companylist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.pat.aapkatrade.R;


/**
 * Created by John on 10/31/2016.
 */
public class CompanyListHolder extends RecyclerView.ViewHolder
{

    View view;
    TextView tvCompanyname,tvDate;
    ImageView imgEdtCompanyName,imgDeleteCompany;


    public CompanyListHolder(View itemView)
    {
        super(itemView);

        imgEdtCompanyName = (ImageView) itemView.findViewById(R.id.imgEdtCompanyName);

        tvCompanyname = (TextView) itemView.findViewById(R.id.tvCompanyname);

        tvDate = (TextView) itemView.findViewById(R.id.tvDate);

        imgDeleteCompany = (ImageView)  itemView.findViewById(R.id.imgDeleteCompany);

        view = itemView;
    }
}