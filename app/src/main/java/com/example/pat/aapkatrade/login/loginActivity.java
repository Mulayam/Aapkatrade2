package com.example.pat.aapkatrade.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;


public class loginActivity extends AppCompatActivity {
    TextView login_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Initview();
        putvalues();
    }

    private void putvalues() {

        login_text.setTextSize(getResources().getDimension(R.dimen.textsize));


    }

    private void Initview() {

        login_text=(TextView)findViewById(R.id.tv_login);


    }
}
