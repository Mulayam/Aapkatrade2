package com.example.pat.aapkatrade.Home.registration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;

public class RegistrationBusinessAssociateActivity extends AppCompatActivity {
    private TextView uploadMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_business_associate);
        initView();
        uploadMsg.setText("Attach Your Photo");
    }

    private void initView() {

        uploadMsg = (TextView) findViewById(R.id.uploadMsg);

    }
}
