package com.example.pat.aapkatrade.search;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.CommomAdapter;
import com.example.pat.aapkatrade.Home.CommomData;
import com.example.pat.aapkatrade.Home.registration.RegistrationActivity;
import com.example.pat.aapkatrade.Home.registration.entity.State;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpStateAdapter;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.App_config;
import com.example.pat.aapkatrade.general.Call_webservice;
import com.example.pat.aapkatrade.general.TaskCompleteReminder;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Utils.adapter.CustomAutocompleteAdapter;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class Search extends AppCompatActivity
{

    AutoCompleteTextView autocomplete_textview_state, autocomplete_textview_product;
    CustomAutocompleteAdapter categoryadapter;
    Context c;
    GridLayoutManager gridLayoutManager;
    RecyclerView recyclerView_search;
    CommomAdapter commomAdapter;
    ArrayList<String> state_names = new ArrayList<>();
    ArrayList<String> product_names = new ArrayList<>();
    ArrayList<CommomData> search_productlist = new ArrayList<>();
    Toolbar toolbar;

    ProgressBarHandler progressBarHandler;
    CoordinatorLayout coordinate_search;
    private ArrayList stateList = new ArrayList<>();
    HashMap<String, String> webservice_header_type = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setuptoolbar();

        initview();

    }


    private void setuptoolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setElevation(0);

        // getSupportActionBar().setIcon(R.drawable.home_logo);

    }

    private void initview()
    {
        call_state_webservice();






        c=Search.this;
        coordinate_search=(CoordinatorLayout)findViewById(R.id.coordinate_search) ;
        progressBarHandler=new ProgressBarHandler(Search.this);

        autocomplete_textview_state=(AutoCompleteTextView)findViewById(R.id.search_autocompletetext_state);
        autocomplete_textview_product=(AutoCompleteTextView)findViewById(R.id.search_autocompletetext_products);
        autocomplete_textview_state.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                String text=s.toString();

                if (text.length() > 2) {
                    Log.e("stateList",stateList.toString());
                    categoryadapter = new CustomAutocompleteAdapter(c, stateList);
                    autocomplete_textview_state.setAdapter(categoryadapter);


//                  String state_search_url="https://aapkatrade.com/slim/location";
//                    call_search_suggest_webservice_state(state_search_url,text);


                }

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        recyclerView_search=(RecyclerView)findViewById(R.id.recycleview_search) ;
        gridLayoutManager = new GridLayoutManager(c, 2);





        autocomplete_textview_product.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String text=s.toString();

                if (text.length() > 2) {

if(autocomplete_textview_state.getText().length()!=0)

{
    String product_search_url = "https://aapkatrade.com/slim/product_search";



    call_search_suggest_webservice_product(product_search_url, text, autocomplete_textview_state.getText().toString().trim());
    Log.e("product_search_data",text+"*****"+autocomplete_textview_state.getText().toString());






}

                }

                }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        autocomplete_textview_product.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {


                if (actionId == EditorInfo.IME_ACTION_SEARCH) {


                    if(autocomplete_textview_state.getText().length()!=0)
                    {
                        if(autocomplete_textview_product.getText().length()!=0)
                        {
                            Log.e("text_editor",autocomplete_textview_state.getText().toString()+"**********"+autocomplete_textview_state.getText().toString());
                            call_search_webservice(autocomplete_textview_state.getText().toString(),autocomplete_textview_product.getText().toString());

                            App_config.hideKeyboard(Search.this);

                        }

                    }




                    return true;
                }
                return false;



            }
        });










    }

    private void call_state_webservice() {
        HashMap<String, String> webservice_body_parameter = new HashMap<>();
        webservice_body_parameter.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        webservice_body_parameter.put("type", "state");
        webservice_body_parameter.put("id", "101");//country id fixed 101 for India

        webservice_header_type.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        Call_webservice.getcountrystatedata(Search.this, "state", getResources().getString(R.string.webservice_base_url) + "/dropdown", webservice_body_parameter, webservice_header_type);

        Call_webservice.taskCompleteReminder = new TaskCompleteReminder() {
            @Override
            public void Taskcomplete(JsonObject state_data_webservice) {
                stateList.clear();
                if (state_data_webservice != null) {

                    JsonObject jsonObject = state_data_webservice.getAsJsonObject();
                    JsonArray jsonResultArray = jsonObject.getAsJsonArray("result");




                    for (int i = 0; i < jsonResultArray.size(); i++) {
                        JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                        //State stateEntity = new State(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString());
                        stateList.add(jsonObject1.get("name").getAsString());
                    }

                    Log.e("stateList",stateList.toString());

                }


                else {
                    Log.e("state_error",state_data_webservice.toString());

                }
            }

        };



    }

    private void call_search_webservice(String location_text, String product_name1) {
        String search_url = "https://aapkatrade.com/slim/search";
        progressBarHandler.show();

        Ion.with(Search.this)
                .load(search_url)
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("location", location_text)
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("name", product_name1)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null) {

                            search_productlist=new ArrayList<CommomData>();

                            JsonObject jsonObject = result.getAsJsonObject();

                            String error = jsonObject.get("error").getAsString();
                            String message = jsonObject.get("message").getAsString();
                            if(message.contains("Failed")) {


                                AndroidUtils.showSnackBar(coordinate_search,"No Suggesstion found");
                                progressBarHandler.hide();

                            }
                            else {

                                Log.e("data2", result.toString());
                                if (jsonObject.get("result").isJsonNull()) {
                                    Log.e("data_jsonArray null", result.toString());
                                }


                                JsonArray jsonarray_result = jsonObject.getAsJsonArray("result");
                                Log.e("data_jsonarray", jsonarray_result.toString());

                                for (int l = 0; l < jsonarray_result.size(); l++) {

                                    JsonObject jsonObject_result = (JsonObject) jsonarray_result.get(l);
                                    String productname = jsonObject_result.get("name").getAsString();
                                    String productid = jsonObject_result.get("id").getAsString();
                                    String product_prize = jsonObject_result.get("price").getAsString();
                                    String imageurl = jsonObject_result.get("image_url").getAsString();
                                    String productlocation=jsonObject_result.get("city_name").getAsString()+","+jsonObject_result.get("state_name").getAsString()+","+
                                            jsonObject_result.get("country_name").getAsString();
                                    search_productlist.add(new CommomData(productid, productname, product_prize, imageurl,productlocation));


                                }

                                recyclerView_search.setLayoutManager(gridLayoutManager);
                                commomAdapter = new CommomAdapter(Search.this, search_productlist, "gridtype", "latestupdate");

                                recyclerView_search.setAdapter(commomAdapter);
                                progressBarHandler.hide();

                            }

                        }
                        else {
                            progressBarHandler.hide();
                        }


//
                    }


                });
    }


    private void call_search_suggest_webservice_product(String product_search_url, String product_search_text, String location_text) {

        Ion.with(Search.this)
                .load(product_search_url)
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("location", location_text.trim())
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("name", product_search_text.trim())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null) {
                            product_names.clear();
                            product_names = new ArrayList<String>();
                            Log.e("webservice_returndata", product_names.toString());
                            JsonObject jsonObject = result.getAsJsonObject();

                            String error = jsonObject.get("error").getAsString();
                            String message = jsonObject.get("message").getAsString();
                            if (message.contains("Failed")) {


                                AndroidUtils.showSnackBar(coordinate_search, "No Suggesstion found");

                            } else {

                                Log.e("data2", result.toString());
                                if (jsonObject.get("result").isJsonNull()) {
                                    Log.e("data_jsonArray null", result.toString());
                                }


                                JsonArray jsonarray_result = jsonObject.getAsJsonArray("result");
                                Log.e("data_jsonarray", jsonarray_result.toString());

                                for (int l = 0; l < jsonarray_result.size(); l++) {

                                    JsonObject jsonObject_result = (JsonObject) jsonarray_result.get(l);
                                    String productname = jsonObject_result.get("name").getAsString();

                                    product_names.add(productname);

                                }


                                if (error.contains("false")) {


                                    Log.e("product_names", product_names.toString());
                                    categoryadapter = new CustomAutocompleteAdapter(c, product_names);
                                    autocomplete_textview_product.setAdapter(categoryadapter);


//


                                }


                            }


                        }


                    }

                });
    }


    }



















    private void call_search_suggest_webservice_state(String url, String input_txt) {



        HashMap<String, String> webservice_body_parameter = new HashMap<>();
        webservice_body_parameter.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        webservice_body_parameter.put("location", input_txt);


        HashMap<String, String> webservice_header_type = new HashMap<>();
        webservice_header_type.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");


        Call_webservice.suggest_search(Search.this, url, "search", webservice_body_parameter, webservice_header_type);

        Call_webservice.taskCompleteReminder = new TaskCompleteReminder() {
            @Override
            public void Taskcomplete(JsonObject webservice_returndata) {


                if (webservice_returndata != null) {
                    Log.e("webservice_returndata", webservice_returndata.toString());
                    JsonObject jsonObject = webservice_returndata.getAsJsonObject();
                    state_names.clear();
                    state_names = new ArrayList<>();
                    String error = jsonObject.get("error").getAsString();
                    String message = jsonObject.get("message").getAsString();


                    if(message.contains("Failed")) {


                        AndroidUtils.showSnackBar(coordinate_search,"No Suggesstion found");


                    }

                    else {


                        Log.e("data2", webservice_returndata.toString());
                        if (jsonObject.get("result").isJsonNull()) {
                            Log.e("data_jsonArray null", webservice_returndata.toString());
                        }


                        JsonArray jsonarray_result = jsonObject.getAsJsonArray("result");
                        Log.e("data_jsonarray", jsonarray_result.toString());

                        for (int l = 0; l < jsonarray_result.size(); l++) {

                            JsonObject jsonObject_top_banner = (JsonObject) jsonarray_result.get(l);
                            String statename = jsonObject_top_banner.get("name").getAsString();

                            state_names.add(statename);

                        }


                        if (error.contains("false")) {
                            Log.e("error_false", "error_false");


                            Log.e("state_names", state_names.toString());
                            categoryadapter = new CustomAutocompleteAdapter(c, state_names);
                            autocomplete_textview_state.setAdapter(categoryadapter);


//


                        } else {
                            //showMessage(message);
                        }

                    }
                }


            }
        };


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
