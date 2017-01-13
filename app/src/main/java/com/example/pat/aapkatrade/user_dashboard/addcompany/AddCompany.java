package com.example.pat.aapkatrade.user_dashboard.addcompany;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;


public class AddCompany extends AppCompatActivity
{

    String[] categoriesNames={"India","China","Australia","Portugle","America","New Zealand"};
    Spinner category, subcategory;
    TextView toolbar_title_txt;
    Button btnSave;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);

        setuptoolbar();

        setup_layout();

    }

    private void setup_layout()
    {

        category = (Spinner) findViewById(R.id.spCategory);

        subcategory = (Spinner) findViewById(R.id.spSubCategory);

        btnSave = (Button) findViewById(R.id.btnSave);

        CustomAdapter customAdapter=new CustomAdapter(getApplicationContext(),categoriesNames);

        category.setAdapter(customAdapter);

        subcategory.setAdapter(customAdapter);

    }


    private void setuptoolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Company");


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
