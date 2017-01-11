package com.example.pat.aapkatrade.login;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Validation;


public class loginActivity extends AppCompatActivity {
    TextView login_text;
    EditText username,password;
    RelativeLayout rl_login;
    Validation vt;
    CoordinatorLayout cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Initview();
        putvalues();
    }

    private void putvalues() {

        login_text.setTextSize(getResources().getDimension(R.dimen.textsize));
        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String input_username=username.getText().toString().trim();
                String input_password=password.getText().toString().trim();

                if (Validation.validate_edittext(username)) {


                    if (Validation.validate_edittext(password)) {






                    }
                    else{
                        showmessage("");
                        password.setError("Invalid Password");
                    }


                }
                else{
                    username.setError("");
                    showmessage("Invalid Username");
                }

            }
        });

    }

    private void Initview() {

        login_text=(TextView)findViewById(R.id.tv_login);
        cl=(CoordinatorLayout)findViewById(R.id.coordinator_layout);
        username= (EditText) findViewById(R.id.etusername);
        password=(EditText)findViewById(R.id.etpassword);
        rl_login=(RelativeLayout)findViewById(R.id.rl_login);
        vt=new Validation();
    }

public void showmessage(String message)
{

    Snackbar snackbar = Snackbar
            .make(cl, message, Snackbar.LENGTH_LONG)
            .setAction("", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Snackbar snackbar1 = Snackbar.make(cl, "", Snackbar.LENGTH_SHORT);
//                    snackbar1.show();
                }
            });



}


}
