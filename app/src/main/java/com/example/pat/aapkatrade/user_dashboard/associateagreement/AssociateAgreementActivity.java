package com.example.pat.aapkatrade.user_dashboard.associateagreement;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.App_sharedpreference;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.productdetail.ProductDetail;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class AssociateAgreementActivity extends Dialog {

    private TextView tvUserName, tvDate, tvReferenceNumber, tvBussinessHeading, tvBussinessDetails, tvMore;
    private boolean isTextViewClicked = false;
    private App_sharedpreference app_sharedpreference;
    private CheckBox agreement_check;
    private Context context;

    public AssociateAgreementActivity(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associate_agreement);
        initView();
        setUpData();

    }
//
//
//    final Dialog dialog = new Dialog(ProductDetail.this);
//                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setContentView(R.layout.fragment_service_enquiry);

    private void setUpData() {
        tvUserName.setText(new StringBuilder().append("Name : ").append(app_sharedpreference.getsharedpref("username")));
        tvDate.setText(new StringBuilder().append("Joining Date : ").append(app_sharedpreference.getsharedpref("created_at")));
        tvReferenceNumber.setText(new StringBuilder().append("Reference Number : ").append(app_sharedpreference.getsharedpref("ref_no")));
        if(app_sharedpreference.getsharedpref("term_accepted").equals("0")){
            agreement_check.setChecked(false);
        }else if(app_sharedpreference.getsharedpref("term_accepted").equals("1")){
            agreement_check.setChecked(true);
        }
        agreement_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    callWebService();
                }
            }
        });
    }

    private void callWebService() {
        Ion.with(context)
                .load("https://aapkatrade.com/slim/term_agreement")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("user_id", app_sharedpreference.getsharedpref("userid", ""))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if(result!=null){
                            if(result.get("error").getAsString().equals("false") && result.get("message").getAsString().equals("Success")){
                                showMessage("Agreement Updated Successfully");
                                app_sharedpreference.setsharedpref("term_accepted", "1");
                                context.startActivity(new Intent(context, HomeActivity.class));
                            }
                        }else {
                            showMessage("Webservice Responding Null");
                        }
                    }
                });
    }

    private void showMessage(String msg){
        AndroidUtils.showSnackBar((LinearLayout)findViewById(R.id.activity_associate_agreement), msg);
    }

    public void initView() {
        app_sharedpreference = new App_sharedpreference(context);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvReferenceNumber = (TextView) findViewById(R.id.tvReferenceNumber);
        tvBussinessHeading = (TextView) findViewById(R.id.tvBussinessHeading);
        tvBussinessDetails = (TextView) findViewById(R.id.tvBussinessDetails);
        agreement_check = (CheckBox) findViewById(R.id.agreement_check);
        tvMore = (TextView) findViewById(R.id.tvMore);
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
                } else {
                    //This will expand the textview if it is of 2 lines
                    tvBussinessDetails.setMaxLines(Integer.MAX_VALUE);
                    isTextViewClicked = true;
                    tvMore.setVisibility(View.INVISIBLE);
                }

            }
        });

    }

}
