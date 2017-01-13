package com.example.pat.aapkatrade.user_dashboard.add_product;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import com.example.pat.aapkatrade.R;


public class AddProductActivity extends AppCompatActivity
{

    Spinner spSubCategory,spCategory,spUnitCategory;
    String[] categoriesNames={"India","China","Australia","Portugle","America","New Zealand"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        setuptoolbar();
        setup_layout();

    }

    private void setup_layout()
    {
        spSubCategory = (Spinner) findViewById(R.id.spSubCategory);

        spCategory = (Spinner) findViewById(R.id.spCategory);

        spUnitCategory = (Spinner) findViewById(R.id.spUnitCategory);

        SpinnerAdapter customAdapter=new SpinnerAdapter(getApplicationContext(),categoriesNames);

        spSubCategory.setAdapter(customAdapter);

        spCategory.setAdapter(customAdapter);

        spUnitCategory.setAdapter(customAdapter);


    }


    private void setuptoolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Add Products");

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
