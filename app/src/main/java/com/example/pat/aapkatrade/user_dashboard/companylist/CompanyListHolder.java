package com.example.pat.aapkatrade.user_dashboard.companylist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.example.pat.aapkatrade.R;


/**
 * Created by John on 10/31/2016.
 */
public class CompanyListHolder extends RecyclerView.ViewHolder
{

    View view;
    TextView tvUsername,tvDate;


    public CompanyListHolder(View itemView)
    {
        super(itemView);

        tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);

        tvDate = (TextView) itemView.findViewById(R.id.tvDate);

        view = itemView;
    }
}