package com.example.pat.aapkatrade.Home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.pat.aapkatrade.R;


/**
 * Created by Netforce on 7/25/2016.
 */
public class CommomHolder extends RecyclerView.ViewHolder {
    RelativeLayout cardview;
    public CommomHolder(View itemView) {
        super(itemView);
        cardview= (RelativeLayout) itemView.findViewById(R.id.cardview);
    }
}
