package com.example.pat.aapkatrade.productdetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.pat.aapkatrade.R;
import java.util.ArrayList;



public class CategoryListFragment extends AppCompatActivity
{

    private RecyclerView mRecyclerView;
    CategoriesListAdapter categoriesListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<CategoriesListData> productListDatas = new ArrayList<>();
    ImageView imageView;
    Toolbar toolbar;
    MaterialDialog materialDialog;
    boolean wrapInScrollView = true;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        setuptoolbar();

        setup_data();

        mRecyclerView = (RecyclerView) findViewById(R.id.product_list_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getApplicationContext());

        mRecyclerView.setLayoutManager(mLayoutManager);

        categoriesListAdapter = new CategoriesListAdapter(getApplicationContext(),productListDatas);

        mRecyclerView.setAdapter(categoriesListAdapter);

        mRecyclerView.setNestedScrollingEnabled(false);





    }


    private void setuptoolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        imageView = (ImageView) findViewById(R.id.imageView1) ;

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(null);

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
        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:

                new MaterialDialog.Builder(this)
                        .title(R.string.app_name)
                        .customView(R.layout.dailog_filter, wrapInScrollView)
                        .show();

                break;


            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }




}
