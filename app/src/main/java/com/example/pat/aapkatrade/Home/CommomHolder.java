package com.example.pat.aapkatrade.Home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Netforce on 7/25/2016.
 */
public class CommomHolder extends RecyclerView.ViewHolder {


    RelativeLayout cardview;
    CircleImageView cimageview;
    TextView tvProductName;


    public CommomHolder(View itemView)
    {
        super(itemView);
        cardview= (RelativeLayout) itemView.findViewById(R.id.cardview);
        cimageview=(CircleImageView)itemView.findViewById(R.id.circular_profile_image) ;

        tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);
    }
}
