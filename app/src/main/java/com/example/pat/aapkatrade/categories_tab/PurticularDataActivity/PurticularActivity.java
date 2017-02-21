package com.example.pat.aapkatrade.categories_tab.PurticularDataActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.categories_tab.CategoriesListAdapter;
import com.example.pat.aapkatrade.categories_tab.CategoriesListData;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.general.recycleview_custom.MyRecyclerViewEffect;
import com.example.pat.aapkatrade.search.Search;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import java.util.ArrayList;
import it.carlom.stikkyheader.core.StikkyHeaderBuilder;


public class PurticularActivity extends AppCompatActivity
{

    com.example.pat.aapkatrade.general.recycleview_custom.MyRecyclerViewEffect mRecyclerView;
    CategoriesListAdapter categoriesListAdapter;
    ArrayList<CategoriesListData> productListDatas = new ArrayList<>();
    ProgressBarHandler progress_handler;
    FrameLayout layout_container,layout_container_relativeSearch;
    MyRecyclerViewEffect myRecyclerViewEffect;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_categories_list);

        setuptoolbar();

        ViewGroup view = (ViewGroup) findViewById(android.R.id.content);

        progress_handler = new ProgressBarHandler(this);

        layout_container = (FrameLayout) view.findViewById(R.id.layout_container);

        mRecyclerView = (com.example.pat.aapkatrade.general.recycleview_custom.MyRecyclerViewEffect) view.findViewById(R.id.recyclerview);

        findViewById(R.id.home_search).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent goto_search=new Intent(PurticularActivity.this,Search.class);
                startActivity(goto_search);
                finish();
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        mRecyclerView.setHasFixedSize(true);

        StikkyHeaderBuilder.stickTo(mRecyclerView)
                .setHeader(R.id.header_simple, view)
                .minHeightHeaderDim(R.dimen.min_header_height)
                .build();

        get_web_data();


    }

    private void get_web_data()
    {
        // layout_container.setVisibility(View.INVISIBLE);
        productListDatas.clear();
        progress_handler.show();

            Ion.with(PurticularActivity.this)
                    .load("http://aapkatrade.com/slim/productlist")
                    .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                    .setBodyParameter("type", "product_list")
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>()
                    {
                        @Override
                        public void onCompleted(Exception e, JsonObject result)
                        {

                            if(result == null)
                            {
                                progress_handler.hide();
                                layout_container.setVisibility(View.INVISIBLE);
                            }
                            else
                            {
                                JsonObject jsonObject = result.getAsJsonObject();

                                String message = jsonObject.get("message").toString().substring(0,jsonObject.get("message").toString().length());

                                String message_data = message.replace("\"", "");

                                System.out.println("message_data=================="+message_data);

                                if (message_data.toString().equals("No record found"))
                                {

                                    progress_handler.hide();
                                    layout_container.setVisibility(View.INVISIBLE);

                                }
                                else
                                {
                                    JsonArray jsonArray = jsonObject.getAsJsonArray("result");

                                    for (int i = 0; i < jsonArray.size(); i++)
                                    {
                                        JsonObject jsonObject2 = (JsonObject) jsonArray.get(i);

                                        String product_id = jsonObject2.get("id").getAsString();

                                        String product_name = jsonObject2.get("name").getAsString();

                                        String product_price = jsonObject2.get("price").getAsString();

                                        String product_cross_price = jsonObject2.get("cross_price").getAsString();

                                        String product_image = jsonObject2.get("image_url").getAsString();

                                        productListDatas.add(new CategoriesListData(product_id, product_name, product_price, product_cross_price, product_image));

                                    }
                                    categoriesListAdapter = new CategoriesListAdapter(PurticularActivity.this, productListDatas);
                                    myRecyclerViewEffect = new MyRecyclerViewEffect(PurticularActivity.this);
                                    mRecyclerView.setAdapter(categoriesListAdapter);

                                    categoriesListAdapter.notifyDataSetChanged();

                                    progress_handler.hide();
                                }

                            }

                        }

                    });




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
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }


}
