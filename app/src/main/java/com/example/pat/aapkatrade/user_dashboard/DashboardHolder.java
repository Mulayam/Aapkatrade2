package com.example.pat.aapkatrade.user_dashboard;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;

/**
 * Created by PPC16 on 10-Jan-17.
 */

public class DashboardHolder extends RecyclerView.ViewHolder {

    View view;
    TextView tvDashboard, tvAmount;
    ImageView imageView;
    LinearLayout relativeDashboard;


    public DashboardHolder(View itemView) {
        super(itemView);

        relativeDashboard = (LinearLayout) itemView.findViewById(R.id.relativeDashboard);

        tvDashboard = (TextView) itemView.findViewById(R.id.tvDashboard);

        imageView = (ImageView) itemView.findViewById(R.id.imgDashboard);

        tvAmount = (TextView) itemView.findViewById(R.id.tvAmount);
        view = itemView;
    }
}