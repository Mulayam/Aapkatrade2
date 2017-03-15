package com.example.pat.aapkatrade.user_dashboard.associateagreement;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.App_sharedpreference;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class AssociateAgreementDialog extends Dialog {


    private TextView tvUserName, tvDate, tvReferenceNumber, tvBussinessHeading, tvBussinessDetails, tvMore;
    private boolean isTextViewClicked = false;
    private App_sharedpreference app_sharedpreference;
    private CheckBox agreement_check;
    private Context context;
    private Button imgCLose;
    private LinearLayout input_layout_agreement;


    public AssociateAgreementDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_associate_agreement);
        initView();
        setUpData();


        imgCLose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) context).finish();
                Intent intent = ((HomeActivity) context).getIntent();

                context.startActivity(intent);
            }
        });
        agreement_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    callWebService();
                }
            }
        });


    }

    private void setUpData() {
        tvUserName.setText(new StringBuilder().append("Name : ").append(app_sharedpreference.getsharedpref("username")));
        tvDate.setText(new StringBuilder().append("Joining Date : ").append(app_sharedpreference.getsharedpref("created_at")));
        tvReferenceNumber.setText(new StringBuilder().append("Reference Number : ").append(app_sharedpreference.getsharedpref("ref_no")));
        if (app_sharedpreference.getsharedpref("term_accepted").equals("0")
                ) {
            agreement_check.setChecked(false);

        } else if (app_sharedpreference.getsharedpref("term_accepted").equals("1")) {

            agreement_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        callWebService();
                    }
                }
            });
        }
    }

    private void callWebService() {
        Ion.with(context)
                .load((context.getResources().getString(R.string.webservice_base_url)) + "/term_agreement")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("user_id", app_sharedpreference.getsharedpref("userid", ""))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null) {
                            if (result.get("error").getAsString().equals("false") && result.get("message").getAsString().equals("Success")) {
                                showMessage("Agreement Updated Successfully");
                                app_sharedpreference.setsharedpref("term_accepted", "1");
                                context.startActivity(new Intent(context, HomeActivity.class));
                            }
                        } else {
                            showMessage("Try Again Later");
                        }
                    }
                });
    }

    private void showMessage(String msg) {
        AndroidUtils.showSnackBar((LinearLayout) findViewById(R.id.activity_associate_agreement), msg);
    }

    public void initView() {
        app_sharedpreference = new App_sharedpreference(context);
        input_layout_agreement = (LinearLayout) findViewById(R.id.input_layout_agreement);
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(8);
        shape.setColor(ContextCompat.getColor(context, R.color.orange));
        input_layout_agreement.setBackground(shape);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvReferenceNumber = (TextView) findViewById(R.id.tvReferenceNumber);
        tvBussinessHeading = (TextView) findViewById(R.id.tvBussinessHeading);
        tvBussinessDetails = (TextView) findViewById(R.id.tvBussinessDetails);
        agreement_check = (CheckBox) findViewById(R.id.agreement_check);
        tvMore = (TextView) findViewById(R.id.tvMore);
        imgCLose = (Button) findViewById(R.id.imgCLose);
        tvMore.setVisibility(View.VISIBLE);
        tvBussinessDetails.setMaxLines(3);
        tvBussinessDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTextViewClicked) {
                    //This will shrink textview to 2 lines if it is expanded.
                    tvBussinessDetails.setMaxLines(3);
                    isTextViewClicked = false;
                    tvMore.setVisibility(View.VISIBLE);
                }
            }
        });

        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTextViewClicked) {
                    //This will shrink textview to 2 lines if it is expanded.
                    tvBussinessDetails.setMaxLines(3);
                    isTextViewClicked = false;
                    tvMore.setText("More...");
                } else {
                    //This will expand the textview if it is of 2 lines
                    tvBussinessDetails.setMaxLines(Integer.MAX_VALUE);
                    isTextViewClicked = true;
                    tvMore.setText("Less...");
                }
            }
        });

    }

}
