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
import com.example.pat.aapkatrade.user_dashboard.product_list.ProductListData;

import java.util.ArrayList;

public class CategoryListFragment extends Fragment
{

    private RecyclerView mRecyclerView;
    CategoriesListAdapter categoriesListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<CategoriesListData> productListDatas = new ArrayList<>();



    public CategoryListFragment()
    {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.activity_product_list, container, false);


        setup_data();

        mRecyclerView = (RecyclerView)v.findViewById(R.id.product_list_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);

        categoriesListAdapter = new CategoriesListAdapter(getActivity(),productListDatas);

        mRecyclerView.setAdapter(categoriesListAdapter);

        mRecyclerView.setNestedScrollingEnabled(false);

        return  v;
    }



    private void setup_data()
    {
        productListDatas.clear();
        try
        {
            productListDatas.add(new CategoriesListData("","",""));
            productListDatas.add(new CategoriesListData("","",""));
            productListDatas.add(new CategoriesListData("","",""));
            productListDatas.add(new CategoriesListData("","",""));

        }catch (Exception  e){

        }
    }





}
