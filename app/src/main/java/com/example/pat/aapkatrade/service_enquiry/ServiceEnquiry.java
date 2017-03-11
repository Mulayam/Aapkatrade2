package com.example.pat.aapkatrade.service_enquiry;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.productdetail.ProductDetail;
import com.example.pat.aapkatrade.user_dashboard.payout.PayoutActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class ServiceEnquiry extends Dialog implements DatePickerDialog.OnDateSetListener {
    EditText firstName,quantity,price,mobile,email,etEndDate,etStatDate,description;
    TextInputLayout input_layout_start_date,input_layout_end_date;
    int isStartDate;
    private String date;
    Context context;



    public ServiceEnquiry(Context context) {
        super(context);
        this.context = context;
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.fragment_service_enquiry);



        initview();


    }

    private void initview() {

        Button imgCLose = (Button)findViewById(R.id.imgCLose);

        firstName  = (EditText)findViewById(R.id.edtFName);

        quantity = (EditText)findViewById(R.id.edtQuantity);

        price = (EditText)findViewById(R.id.edtPrice);

        mobile = (EditText)findViewById(R.id.edtMobile);

        email = (EditText)findViewById(R.id.edtEmail);

        etEndDate = (EditText)findViewById(R.id.etEndDate);


        etStatDate = (EditText)findViewById(R.id.etStartDate) ;
        input_layout_start_date=(TextInputLayout)findViewById(R.id. input_layout_start_date);
        input_layout_end_date=(TextInputLayout)findViewById(R.id. input_layout_end_date);

        description = (EditText) findViewById(R.id.edtDescription);

        Button submit = (Button) findViewById(R.id.buttonSubmit) ;




        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


            }
        });


        imgCLose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                //dialog.hide();
            }
        });
        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius( 8 );
        shape.setColor(ContextCompat.getColor(context, R.color.orange));
       findViewById(R.id.buttonSubmit).setBackground(shape);
        GradientDrawable shape2 =  new GradientDrawable();
        shape2.setCornerRadius( 8 );
        shape2.setColor(ContextCompat.getColor(context, R.color.green));
       findViewById(R.id.rl_service_enquiry).setBackground(shape2);
        input_layout_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        input_layout_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        etStatDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("etStartDate","clicked");

                isStartDate = 0;
                openCalender();

            }
        });
        etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("etStartDate","clicked");
                isStartDate = 1;
                openCalender();
            }
        });










    }



    private void openCalender() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setMaxDate(now);
        if(isStartDate == 1){
            if(etStatDate.getText()!=null || etStatDate.getText().toString().length()>=8)
                dpd.setMinDate(AndroidUtils.stringToCalender(etStatDate.getText().toString()));
        }
        final Activity activity = (Activity) context;

        // Return the fragment manager


        dpd.show(activity.getFragmentManager(), "DatePickerDialog");











    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        showDate(year, monthOfYear + 1, dayOfMonth);
    }

    private void showDate(int year, int month, int day) {
        date = (new StringBuilder()).append(year).append("-").append(month).append("-").append(day).toString();
        if(isStartDate == 0){
            etStatDate.setText(date);
        } else if(isStartDate == 1){
            etEndDate.setText(date);
        }
    }
}
