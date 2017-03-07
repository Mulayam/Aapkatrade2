package com.example.pat.aapkatrade.user_dashboard.payout;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;

/**
 * Created by PPC16 on 06-Mar-17.
 */

public class PayoutHolder extends RecyclerView.ViewHolder
{

    View view;
    TextView tvCompanyname,tvDate;
    ImageView imgEdtCompanyName,imgDeleteCompany,imgNext;
    RelativeLayout relativecompanyList;
    LinearLayout linearLayoutDetail;
    View View1;


    public PayoutHolder(View itemView)
    {
        super(itemView);

        View1 = (View) itemView.findViewById(R.id.View1);

        linearLayoutDetail = (LinearLayout)  itemView.findViewById(R.id.linearLayoutDetail);

        relativecompanyList = (RelativeLayout) itemView.findViewById(R.id.relativecompanyList);

        imgNext = (ImageView) itemView.findViewById(R.id.imgNext);

        imgEdtCompanyName = (ImageView) itemView.findViewById(R.id.imgEdit);

        tvCompanyname = (TextView) itemView.findViewById(R.id.tvCompanyname);

        tvDate = (TextView) itemView.findViewById(R.id.tvDate);

        imgDeleteCompany = (ImageView)  itemView.findViewById(R.id.imgDelete);

        view = itemView;
    }
}
