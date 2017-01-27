package com.example.pat.aapkatrade.user_dashboard.product_list;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.pat.aapkatrade.R;

/**
 * Created by PPC16 on 11-Jan-17.
 */

public class ProductListHolder extends RecyclerView.ViewHolder
{


    View view;
    TextView tvProductName,tvDate;
    RelativeLayout relativeProductList;



    public ProductListHolder(View itemView)
    {
        super(itemView);

        relativeProductList = (RelativeLayout) itemView.findViewById(R.id.relativeProductList);

        tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);

        tvDate = (TextView) itemView.findViewById(R.id.tvDate);

        view = itemView;
    }
}