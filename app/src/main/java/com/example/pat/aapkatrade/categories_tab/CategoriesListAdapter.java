package com.example.pat.aapkatrade.categories_tab;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.CheckPermission;
import com.example.pat.aapkatrade.general.LocationManager_check;
import com.example.pat.aapkatrade.map.GoogleMapActivity;
import com.example.pat.aapkatrade.productdetail.ProductDetail;
import com.koushikdutta.ion.Ion;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by PPC16 on 16-Jan-17.
 */

public class CategoriesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater inflater;
    private List<CategoriesListData> itemList;
    public Activity context;
    CategoriesListHolder viewHolder;


    public CategoriesListAdapter(Activity context, List<CategoriesListData> itemList) {
        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.product_list_item, parent, false);

        viewHolder = new CategoriesListHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        CategoriesListHolder homeHolder = (CategoriesListHolder) holder;

        homeHolder.tvProductName.setText(itemList.get(position).product_name);

        homeHolder.tvProductPrice.setText("\u20A8" + "." + " " + itemList.get(position).product_price);

        homeHolder.tvProductCrossPrice.setText("\u20A8" + "." + " " + itemList.get(position).product_cross_price);

        Ion.with(homeHolder.productimage).load(itemList.get(position).product_image);

        homeHolder.linearlayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ProductDetail.class);
                intent.putExtra("product_id", itemList.get(position).product_id);
                intent.putExtra("product_location", itemList.get(position).product_location);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            }
        });


        homeHolder.linearlayoutMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                boolean permission_status = CheckPermission.checkPermissions(context);


                if (permission_status)

                {
                    LocationManager_check locationManagerCheck = new LocationManager_check(
                            context);
                    Location location = null;
                    if (locationManagerCheck.isLocationServiceAvailable())


                    {


                        Intent intent = new Intent(context, GoogleMapActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        intent.putExtra("product_location", itemList.get(position).product_location);
                        context.startActivity(intent);


                    } else {
                        locationManagerCheck.createLocationServiceError(context);
                    }

                }


            }
        });


    }

    private void showMessage(String s) {

        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getItemCount() {
        return itemList.size();
        // return itemList.size();
    }

    public String getCurrentTimeStamp() {
        return new SimpleDateFormat("dd MMM yyyy HH:mm").format(new Date());
    }


}
