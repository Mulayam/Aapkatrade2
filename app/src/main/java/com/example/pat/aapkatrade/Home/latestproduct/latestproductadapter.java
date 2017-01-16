package com.example.pat.aapkatrade.Home.latestproduct;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pat.aapkatrade.Home.CommomData;
import com.example.pat.aapkatrade.R;

import java.util.ArrayList;

/**
 * Created by Netforce on 7/25/2016.
 */
public class latestproductadapter extends RecyclerView.Adapter<Holder_latestproduct> {
    Context context;
    ArrayList<CommomData> commomDatas;

    public latestproductadapter(Context context, ArrayList<CommomData> commomDatas) {
        this.context = context;
        this.commomDatas = commomDatas;
    }

    @Override
    public Holder_latestproduct onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_latestproduct, parent, false);
        Holder_latestproduct viewHolder = new Holder_latestproduct(view);


        //RecyclerView.ViewHolder viewHolder;







        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Holder_latestproduct holder, int position) {
//        Picasso.with(context).load("http://administrator.aapkatrade.com/public/upload/banner/dairy-product.jpg")
//                .error(R.drawable.banner)
//                .into(holder.profile_image);


        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

//        holder.cardview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(context,ProductListActivity.class);
//                context.startActivity(intent);
//                ((AppCompatActivity)context).overridePendingTransition(R.anim.enter, R.anim.exit);
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return commomDatas.size();
    }








}
