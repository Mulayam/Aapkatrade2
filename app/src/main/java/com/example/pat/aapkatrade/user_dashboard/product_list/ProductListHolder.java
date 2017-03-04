package com.example.pat.aapkatrade.user_dashboard.product_list;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.pat.aapkatrade.R;

/**
 * Created by PPC16 on 11-Jan-17.
 */

public class ProductListHolder extends RecyclerView.ViewHolder
{

    View view;
    TextView tvProductName,tvCategoriesName,tvProductPrice;
    LinearLayout linearlayout1;
    ImageView imgProduct,imgMore;


    public ProductListHolder(View itemView)
    {
        super(itemView);

        linearlayout1 = (LinearLayout) itemView.findViewById(R.id.linearlayout1);

        tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);

        tvCategoriesName = (TextView) itemView.findViewById(R.id.tvCategoriesName);

        tvProductPrice = (TextView)  itemView.findViewById(R.id.tvProductPrice);

        imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);

        imgMore = (ImageView) itemView.findViewById(R.id.imgMore);


        view = itemView;
    }
}