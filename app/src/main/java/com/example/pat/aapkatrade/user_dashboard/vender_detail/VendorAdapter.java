package com.example.pat.aapkatrade.user_dashboard.vender_detail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.pat.aapkatrade.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by PPC16 on 09-Feb-17.
 */

public class VendorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{


    final LayoutInflater inflater;
    List<VendorData> itemList;
    Context context;
    VendorHolder viewHolder;
    VendorActivity vendorActivity;




    public VendorAdapter(Context context, List<VendorData> itemList,VendorActivity vendorActivity)
    {
        this.vendorActivity = vendorActivity;
        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = inflater.inflate(R.layout.row_vendor_list, parent, false);

        viewHolder = new VendorHolder(view);

        System.out.println("data-----------"+itemList);

        return viewHolder;


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {

        final VendorHolder homeHolder = (VendorHolder) holder;


    }

    private void showMessage(String s)
    {

        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getItemCount()
    {
        return itemList.size();
    }

    public String getCurrentTimeStamp()
    {
        return new SimpleDateFormat("dd MMM yyyy HH:mm").format(new Date());
    }
}