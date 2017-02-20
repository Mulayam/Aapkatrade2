package com.example.pat.aapkatrade.user_dashboard.product_list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.categories_tab.CategoriesListAdapter;
import com.example.pat.aapkatrade.categories_tab.CategoriesListData;
import com.example.pat.aapkatrade.categories_tab.CategoryListActivity;
import com.example.pat.aapkatrade.general.App_sharedpreference;
import com.example.pat.aapkatrade.general.animation_effects.App_animation;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.general.recycleview_custom.MyRecyclerViewEffect;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity
{

    RecyclerView product_list;
    ProductListAdapter productListAdapter;
    ProgressBarHandler progress_handler;
    App_sharedpreference app_sharedpreference;
    String user_id;
    LinearLayout layout_container;
    ArrayList<ProductListData> productListDatas = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_product);

        progress_handler = new ProgressBarHandler(this);

        app_sharedpreference = new App_sharedpreference(this);

        user_id = app_sharedpreference.getsharedpref("userid","");

        setuptoolbar();

        setup_layout();

        get_web_data();

    }


    private void setup_layout()
    {
        product_list = (RecyclerView) findViewById(R.id.product_list_recycler_view);

        layout_container = (LinearLayout)  findViewById(R.id.layout_container);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        product_list.setLayoutManager(mLayoutManager);

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


    private void get_web_data()
    {
        // layout_container.setVisibility(View.INVISIBLE);
        productListDatas.clear();
        progress_handler.show();

            Ion.with(ProductListActivity.this)
                    .load("http://aapkatrade.com/slim/productlist")
                    .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                    .setBodyParameter("type", "product_list")
                    .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                    .setBodyParameter("seller_id ",user_id)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>()
                    {
                        @Override
                        public void onCompleted(Exception e, JsonObject result)
                        {

                            System.out.println("jsonObject-------------"+result.toString());

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

                                        String category_name = jsonObject2.get("category_name").getAsString();

                                        productListDatas.add(new ProductListData(product_id, product_name, product_price, product_cross_price, product_image,category_name));

                                    }

                                    productListAdapter = new ProductListAdapter(getApplicationContext(), productListDatas);

                                    product_list.setAdapter(productListAdapter);

                                    productListAdapter.notifyDataSetChanged();

                                    progress_handler.hide();



                                }

                                //   layout_container.setVisibility(View.VISIBLE);
                            }

                        }
                    });

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
