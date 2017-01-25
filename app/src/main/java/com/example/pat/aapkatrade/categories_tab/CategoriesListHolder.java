package com.example.pat.aapkatrade.categories_tab;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.pat.aapkatrade.R;

/**
 * Created by PPC16 on 16-Jan-17.
 */

public class CategoriesListHolder extends RecyclerView.ViewHolder
{


    View view;
    LinearLayout linearlayout1;

    public CategoriesListHolder(View itemView)
    {
        super(itemView);


        linearlayout1 = (LinearLayout) itemView.findViewById(R.id.linearlayout1) ;


        view = itemView;
    }
}