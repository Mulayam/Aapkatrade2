package com.example.pat.aapkatrade.productdetail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.user_dashboard.product_list.ProductListData;
import com.example.pat.aapkatrade.user_dashboard.product_list.ProductListHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by PPC16 on 16-Jan-17.
 */

public class CategoriesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{



    private final LayoutInflater inflater;
    private List<CategoriesListData> itemList;
    private Context context;
    CategoriesListHolder viewHolder;


    public CategoriesListAdapter(Context context, List<CategoriesListData> itemList)
    {
        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = inflater.inflate(R.layout.product_list_item, parent, false);
        viewHolder = new CategoriesListHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {

        CategoriesListHolder homeHolder = (CategoriesListHolder) holder;

        homeHolder.linearlayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context,ProductDetail.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
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
