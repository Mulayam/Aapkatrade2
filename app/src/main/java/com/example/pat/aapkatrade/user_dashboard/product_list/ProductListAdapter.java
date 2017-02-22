package com.example.pat.aapkatrade.user_dashboard.product_list;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.categories_tab.CategoriesListData;
import com.example.pat.aapkatrade.general.animation_effects.App_animation;
import com.example.pat.aapkatrade.productdetail.ProductDetail;
import com.example.pat.aapkatrade.user_dashboard.companylist.CompanyData;
import com.example.pat.aapkatrade.user_dashboard.companylist.CompanyListHolder;
import com.example.pat.aapkatrade.user_dashboard.product_list.listproduct_detail.ListProductDetailActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by PPC16 on 11-Jan-17.
 */

public class ProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

     final LayoutInflater inflater;
     List<ProductListData> itemList;
     Context context;
     ProductListHolder viewHolder;



    public ProductListAdapter(Context context, List<ProductListData> itemList)
    {
        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = inflater.inflate(R.layout.row_productlist, parent, false);
        viewHolder = new ProductListHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {

        ProductListHolder homeHolder = (ProductListHolder) holder;

        homeHolder.tvProductName.setText(itemList.get(position).product_name);

        homeHolder.tvProductPrice.setText("\u20A8"+" "+itemList.get(position).product_price);

        homeHolder.tvCategoriesName.setText(itemList.get(position).category_name);


        homeHolder.linearlayout1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                Intent product_detail = new Intent(context, ListProductDetailActivity.class);
                context.startActivity(product_detail);

            }
        });




    }

    private void showMessage(String s)
    {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getItemCount()
    {
        return itemList.size();
        // return itemList.size();
    }

    public String getCurrentTimeStamp()
    {
        return new SimpleDateFormat("dd MMM yyyy HH:mm").format(new Date());
    }
}
