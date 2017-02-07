package com.example.pat.aapkatrade.general.Utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;

import java.util.ArrayList;

/**
 * Created by PPC21 on 06-Feb-17.
 */

public class CustomAutocompleteAdapter extends BaseAdapter implements Filterable{
    Context context;
    ArrayList<String> categoriesNames;
    LayoutInflater inflter;
    ArrayList<String> categoriesids;

    public CustomAutocompleteAdapter(Context applicationContext, ArrayList<String> categoriesNames,ArrayList<String> categoriesids) {
        this.context = applicationContext;

        this.categoriesNames = categoriesNames;
        this.categoriesids=categoriesids;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return categoriesNames.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override


    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.row_spinner, null);
        TextView names = (TextView) view.findViewById(R.id.tvSpCategory);
        names.setText(categoriesNames.get(i));
        return view;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}