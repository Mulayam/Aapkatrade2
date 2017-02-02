package com.example.pat.aapkatrade.categories_tab;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.productdetail.*;
import com.mikepenz.iconics.utils.Utils;

import java.util.ArrayList;

import it.carlom.stikkyheader.core.StikkyHeaderBuilder;
import it.carlom.stikkyheader.core.animator.AnimatorBuilder;
import it.carlom.stikkyheader.core.animator.HeaderStikkyAnimator;


public class CategoryListActivity extends AppCompatActivity
{



    RecyclerView mRecyclerView;
    CategoriesListAdapter categoriesListAdapter;
    ArrayList<CategoriesListData> productListDatas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_categories_list);

        setuptoolbar();

        setup_data();
       // setupCategoriesProductFragment();

        ViewGroup view = (ViewGroup) findViewById(android.R.id.content);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);

        StikkyHeaderBuilder.stickTo(mRecyclerView)
                .setHeader(R.id.header_simple, view)
                .minHeightHeaderDim(R.dimen.min_header_height)
                .build();

        categoriesListAdapter = new CategoriesListAdapter(getApplicationContext(), productListDatas);

        mRecyclerView.setAdapter(categoriesListAdapter);


    }


    private void setuptoolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(null);

        //getSupportActionBar().setIcon(R.drawable.home_logo);

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
            productListDatas.add(new CategoriesListData("","",""));
            productListDatas.add(new CategoriesListData("","",""));
            productListDatas.add(new CategoriesListData("","",""));
            productListDatas.add(new CategoriesListData("","",""));

        }
        catch (Exception  e)
        {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }


}
