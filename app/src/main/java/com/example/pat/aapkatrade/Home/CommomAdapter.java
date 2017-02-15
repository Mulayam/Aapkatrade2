package com.example.pat.aapkatrade.Home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Tabletsize;
import com.example.pat.aapkatrade.general.animation_effects.App_animation;
import com.example.pat.aapkatrade.productdetail.ProductDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Netforce on 7/25/2016.
 */
public class CommomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<CommomData> commomDatas;
    String arrangementtype, categorytype;
    View v;


    public CommomAdapter(Context context, ArrayList<CommomData> commomDatas, String arrangementtype, String categorytype) {
        this.context = context;
        this.commomDatas = commomDatas;
        this.arrangementtype = arrangementtype;
        this.categorytype = categorytype;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        CommomHolder viewHolder1;
        CommonHolder_grid viewHolder2;

        //RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (arrangementtype == "list") {
            v = inflater.inflate(R.layout.row_dashboard, parent, false);
            viewHolder2 = new CommonHolder_grid(v);
            return viewHolder2;


        } else {
            v = inflater.inflate(R.layout.row_dashboard_grid, parent, false);
            viewHolder1 = new CommomHolder(v);
            return viewHolder1;
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewholder, final int position) {
        if (arrangementtype == "list")

        {
            CommomHolder holder = new CommomHolder(v);


            Picasso.with(context).load(commomDatas.get(position).imageurl)
                    .error(R.drawable.banner)
                    .placeholder(R.drawable.default_noimage)
                    .error(R.drawable.default_noimage)
                    .into(holder.cimageview);
//            Animation a = AnimationUtils.loadAnimation(context, R.anim.show_progress);
//            a.setDuration(1000);
//            holder.cimageview.startAnimation(a);


//        Ion.with(holder.cimageview)
//                 .placeholder(R.drawable.ms__drawable)
//
//                .load(commomDatas.get(0).imageurl);
            Log.e("imageurl", commomDatas.get(0).imageurl);

            holder.cardview.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {

                Intent intent = new Intent(context,ProductDetail.class);
                    intent.putExtra("product_id",commomDatas.get(position).id.toString());
                context.startActivity(intent);
                ((AppCompatActivity) context).overridePendingTransition(R.anim.enter, R.anim.exit);

                }
            });
            holder.tvProductName.setText(commomDatas.get(position).name);

        }
        else {

            final CommonHolder_grid grid_holder = new CommonHolder_grid(v);

            Picasso.with(context).load(commomDatas.get(position).imageurl)
                    .error(R.drawable.default_noimage)
                    .placeholder(R.drawable.default_noimage)
                    .into(grid_holder.product_imageview);
            grid_holder.tvProductName.setText(commomDatas.get(position).name);
            grid_holder.rl_grid_row_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProductDetail.class);
                    intent.putExtra("product_id",commomDatas.get(position).id.toString());
                    context.startActivity(intent);
                    ((AppCompatActivity) context).overridePendingTransition(R.anim.enter, R.anim.exit);
                }
            });


//            Animation a = AnimationUtils.loadAnimation(context, R.anim.show_progress);
//            a.setDuration(1000);
//            grid_holder.product_imageview.startAnimation(a);

            if (Tabletsize.isTablet(context)) {

                if (position % 3 == 0) {

                    grid_holder.view_grid_left.setVisibility(View.GONE);
                    grid_holder.view_grid_right.setVisibility(View.GONE);


                } else {
                    grid_holder.view_grid_left.setVisibility(View.VISIBLE);
                    grid_holder.view_grid_right.setVisibility(View.GONE);
                }


            } else {
                if (position % 2 == 0) {

                    grid_holder.view_grid_left.setVisibility(View.GONE);
                    grid_holder.view_grid_right.setVisibility(View.GONE);


                } else {
                    grid_holder.view_grid_left.setVisibility(View.VISIBLE);
                    grid_holder.view_grid_right.setVisibility(View.GONE);
                }


            }
        }




    }

    @Override
    public int getItemCount() {
        return commomDatas.size();
    }


}
