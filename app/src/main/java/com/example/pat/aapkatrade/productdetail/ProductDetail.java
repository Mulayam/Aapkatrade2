package com.example.pat.aapkatrade.productdetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.example.pat.aapkatrade.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class ProductDetail extends AppCompatActivity {
    MaterialSpinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        String[] ITEMS = {"1", "2", "3", "4", "5", "6"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (MaterialSpinner) findViewById(R.id.spinner_qty);
        spinner.setAdapter(adapter);
    }
}
