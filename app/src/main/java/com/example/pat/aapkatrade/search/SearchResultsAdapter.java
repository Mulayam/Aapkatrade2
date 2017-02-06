package com.example.pat.aapkatrade.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;

import java.util.ArrayList;

/**
 * Created by PPC21 on 06-Feb-17.
 */

public class SearchResultsAdapter extends BaseAdapter
{
    private LayoutInflater layoutInflater;

    private ArrayList<String> productDetails=new ArrayList<String>();
    int count;

    Context context;

    //constructor method
    public SearchResultsAdapter(Context context, ArrayList<String> product_details) {

        layoutInflater = LayoutInflater.from(context);

        this.productDetails=product_details;
        this.count= product_details.size();
        this.context = context;


    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int arg0) {
        return productDetails.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        ViewHolder holder;
        //Product tempProduct = productDetails.get(position);

        if (convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.list_search, null);
            holder = new ViewHolder();
            holder.product_name = (TextView) convertView.findViewById(R.id.tv_productname);


            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }




        return convertView;
    }

    static class ViewHolder
    {
        TextView product_name;


    }

}