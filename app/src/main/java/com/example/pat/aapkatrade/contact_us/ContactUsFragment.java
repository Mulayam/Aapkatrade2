package com.example.pat.aapkatrade.contact_us;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.user_dashboard.addcompany.AddCompany;
import com.example.pat.aapkatrade.user_dashboard.companylist.CompanyList;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class ContactUsFragment extends Fragment
{

    EditText etSubject,etUserName,etMobileNo,etEmail,etQuery;
    Button buttonSave;
    ProgressBarHandler progress_handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);

        progress_handler = new ProgressBarHandler(getActivity());

        setup_layout(view);

        return view;
    }

    private void setup_layout(View v)
    {

        etSubject = (EditText) v.findViewById(R.id.etSubject);

        etUserName = (EditText) v.findViewById(R.id.etUserName);

        etMobileNo = (EditText) v.findViewById(R.id.etMobileNo);

        etEmail = (EditText) v.findViewById(R.id.etEmail);

        etQuery = (EditText) v.findViewById(R.id.etQuery);

        buttonSave = (Button)v.findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

            String subject = etSubject.getText().toString();
            String username = etUserName.getText().toString();
            String mobileno = etMobileNo.getText().toString();
            String email = etEmail.getText().toString();
            String query = etQuery.getText().toString();

            callAddCompanyWebService(subject,username,mobileno,email,query);

            }
        });



    }


    private void callAddCompanyWebService(String subject, String username , String mobile, String email, String query)
    {

        progress_handler.show();

        Ion.with(getActivity())
                .load("https://aapkatrade.com/slim/contact")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("name", username)
                .setBodyParameter("email", email)
                .setBodyParameter("mobile",mobile)
                .setBodyParameter("message", query)
                .setBodyParameter("subject", subject)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>()
                {


                    @Override
                    public void onCompleted(Exception e, JsonObject result)
                    {

                        if (result == null)
                        {

                            progress_handler.hide();

                        }
                        else
                        {
                            JsonObject jsonObject = result.getAsJsonObject();
                            String message = jsonObject.get("message").getAsString();
                            Log.e("message", message);
                            progress_handler.hide();


                        }
                    }
                });
    }



}
