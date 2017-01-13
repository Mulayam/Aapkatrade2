package com.example.pat.aapkatrade.productdetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.pat.aapkatrade.R;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "RecyclerViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.product_list_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
//        RecyclerView.ItemDecoration itemDecoration =
//                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
//        mRecyclerView.addItemDecoration(itemDecoration);

    }

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
