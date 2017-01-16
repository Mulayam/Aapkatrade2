package com.example.pat.aapkatrade.productdetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pat.aapkatrade.R;

import java.util.ArrayList;

public class CategoryListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "RecyclerViewActivity";

    Toolbar toolbar;

    public CategoryListFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_product_list, container, false);

        //setuptoolbar();
        mRecyclerView = (RecyclerView)v.findViewById(R.id.product_list_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
//        RecyclerView.ItemDecoration itemDecoration =
//                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
//        mRecyclerView.addItemDecoration(itemDecoration);
return  v;
    }

//    private void setuptoolbar() {
//
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setTitle(null);
//        getSupportActionBar().setIcon(R.drawable.home_logo);
//    }

    public ArrayList<String> getDataSet() {

        ArrayList<String> dataSet = new ArrayList<>();
        dataSet.add("Item123");
        dataSet.add("Item124");
        dataSet.add("Item125");
        dataSet.add("Item126");
        dataSet.add("Item127");
        dataSet.add("Item128");
        dataSet.add("Item129");
        dataSet.add("Item130");

        return dataSet;
    }
}
