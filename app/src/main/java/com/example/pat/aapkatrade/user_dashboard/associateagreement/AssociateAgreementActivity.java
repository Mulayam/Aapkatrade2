package com.example.pat.aapkatrade.user_dashboard.associateagreement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;
public class AssociateAgreementActivity extends AppCompatActivity
{

    TextView tvUserName,tvDate,tvReferenceNumber,tvBussinessHeading,tvBussinessDetails,tvMore;
    boolean isTextViewClicked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_associate_agreement);

        setuptoolbar();

        setup_layout();

        get_webservice_data();

    }

    private void get_webservice_data() {

    }

    public void setup_layout()
    {
        tvUserName = (TextView) findViewById(R.id.tvUserName);

        tvDate = (TextView) findViewById(R.id.tvDate);

        tvReferenceNumber = (TextView) findViewById(R.id.tvReferenceNumber);

        tvBussinessHeading = (TextView) findViewById(R.id.tvBussinessHeading);

        tvBussinessDetails = (TextView) findViewById(R.id.tvBussinessDetails);

        tvMore = (TextView) findViewById(R.id.tvMore);

        tvMore.setVisibility(View.VISIBLE);
        tvBussinessDetails.setMaxLines(3);

        tvBussinessDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isTextViewClicked)
                {
                    //This will shrink textview to 2 lines if it is expanded.
                    tvBussinessDetails.setMaxLines(3);
                    isTextViewClicked = false;
                    tvMore.setVisibility(View.VISIBLE);
                }
            }
        });

        tvMore.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(isTextViewClicked)
                {
                    //This will shrink textview to 2 lines if it is expanded.
                    tvBussinessDetails.setMaxLines(3);
                    isTextViewClicked = false;
                }
                else
                {
                    //This will expand the textview if it is of 2 lines
                    tvBussinessDetails.setMaxLines(Integer.MAX_VALUE);
                    isTextViewClicked = true;
                    tvMore.setVisibility(View.INVISIBLE);
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
        // getSupportActionBar().setIcon(R.drawable.home_logo);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.empty_menu, menu);
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
