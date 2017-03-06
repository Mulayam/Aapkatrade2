package com.example.pat.aapkatrade.user_dashboard.payout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.user_dashboard.address.AddressData;
import com.example.pat.aapkatrade.user_dashboard.address.AddressListAdapter;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

public class PayoutActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
{


    private Context context;
    private int isStartDate = -1;
    private EditText etStartDate, etEndDate;
    private ImageView openStartDateCal, openEndDateCal;
    private String date;
    ArrayList<PayoutData> payoutDatas = new ArrayList<>();
    RecyclerView payoutList;
    PayoutAdapter payoutAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payout);

        setuptoolbar();

        initView();

        setup_data();

        setup_layout();

        openStartDateCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartDate = 0;
                openCalender();
            }
        });
        openEndDateCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartDate = 1;
                openCalender();
            }
        });
    }

    private void setup_layout()
    {

        payoutList = (RecyclerView) findViewById(R.id.payoutList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        payoutAdapter = new PayoutAdapter(getApplicationContext(), payoutDatas);

        payoutList.setAdapter(payoutAdapter);

        payoutList.setLayoutManager(mLayoutManager);

    }

    private void initView()
    {
        context = PayoutActivity.this;
        etStartDate = (EditText) findViewById(R.id.etStartDate);
        etEndDate = (EditText) findViewById(R.id.etEndDate);
        openStartDateCal = (ImageView) findViewById(R.id.openStartDateCal);
        openEndDateCal = (ImageView) findViewById(R.id.openEndDateCal);
    }


    private void setuptoolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setElevation(0);
        }
    }


    private void openCalender() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                PayoutActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setMaxDate(now);
        if(isStartDate == 1){
            if(etStartDate.getText()!=null || etStartDate.getText().toString().length()>=8)
            dpd.setMinDate(AndroidUtils.stringToCalender(etStartDate.getText().toString()));
        }
        dpd.show(getFragmentManager(), "DatePickerDialog");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.empty_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        showDate(year, monthOfYear + 1, dayOfMonth);
    }

    private void showDate(int year, int month, int day) {
        date = (new StringBuilder()).append(year).append("-").append(month).append("-").append(day).toString();
        if(isStartDate == 0){
            etStartDate.setText(date);
        } else if(isStartDate == 1){
            etEndDate.setText(date);
        }
    }


    private void setup_data()
    {
        payoutDatas.clear();
        try
        {
            payoutDatas.add(new PayoutData("Line 1", "Line 2", "Line 3"));
            payoutDatas.add(new PayoutData("Line 1", "Line 2", "Line 3"));
            payoutDatas.add(new PayoutData("Line 1", "Line 2", "Line 3"));
            payoutDatas.add(new PayoutData("Line 1", "Line 2", "Line 3"));
            payoutDatas.add(new PayoutData("Line 1", "Line 2", "Line 3"));
            payoutDatas.add(new PayoutData("Line 1", "Line 2", "Line 3"));
        }
        catch (Exception e)
        {

        }
    }

}
