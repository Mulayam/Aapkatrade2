package com.example.pat.aapkatrade.Home.latestproduct;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.pat.aapkatrade.R;

/**
 * Created by PPC21 on 12-Jan-17.
 */
public class Holder_latestproduct  extends RecyclerView.ViewHolder{
    RelativeLayout rl;
   ImageView profile_image;

    public Holder_latestproduct(View itemView) {

        super(itemView);
        rl=(RelativeLayout)itemView.findViewById(R.id.rl_latest_product);
        profile_image=(ImageView)itemView.findViewById(R.id.profile_image);
    }
}
