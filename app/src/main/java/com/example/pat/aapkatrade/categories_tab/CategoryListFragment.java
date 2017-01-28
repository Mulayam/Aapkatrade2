package com.example.pat.aapkatrade.categories_tab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.productdetail.*;
import com.mikepenz.iconics.utils.Utils;

import java.util.ArrayList;

import it.carlom.stikkyheader.core.StikkyHeaderBuilder;


public class CategoryListFragment extends AppCompatActivity
{



    private RecyclerView mRecyclerView;
    CategoriesListAdapter categoriesListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<CategoriesListData> productListDatas = new ArrayList<>();
    ImageView imageView;
    Toolbar toolbar;
    boolean wrapInScrollView = true;
    Context context;
    LinearLayout linearLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        context = this;

        setuptoolbar();

        setup_data();

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        mRecyclerView = (RecyclerView) findViewById(R.id.product_list_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getApplicationContext());

        mRecyclerView.setLayoutManager(mLayoutManager);

        categoriesListAdapter = new CategoriesListAdapter(getApplicationContext(),productListDatas);

        mRecyclerView.setAdapter(categoriesListAdapter);


       /* mRecyclerView.setHasFixedSize(true);

        StikkyHeaderBuilder.stickTo(mRecyclerView)

                .setHeader(R.id.header,linearLayout)
                .minHeightHeaderDim(R.dimen.min_height_header)
                .build();
      */



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
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_filtre, menu);
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

            case R.id.login:

                Intent i = new Intent(CategoryListFragment.this, FilterActivity.class);
                startActivity(i);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }




}
