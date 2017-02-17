package com.example.pat.aapkatrade.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.example.pat.aapkatrade.Home.CommomAdapter;
import com.example.pat.aapkatrade.Home.CommomData;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Call_webservice;
import com.example.pat.aapkatrade.general.TaskCompleteReminder;
import com.example.pat.aapkatrade.general.Utils.adapter.CustomAutocompleteAdapter;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Search extends AppCompatActivity
{

    AutoCompleteTextView autocomplete_textview;
    CustomAutocompleteAdapter categoryadapter;
    ArrayList<String> product_ids=new ArrayList<>();
    ArrayList<String> product_names=new ArrayList<>();
    Context c;
    GridLayoutManager gridLayoutManager;
    RecyclerView recyclerView_search;
    CommomAdapter commomAdapter;
    ArrayList<CommomData> search_productlist = new ArrayList<>();
    String[] language ={"C","C++","Java",".NET","iPhone","Android","ASP.NET","PHP"};
    Toolbar toolbar;



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

        // getSupportActionBar().setIcon(R.drawable.home_logo);

    }
    private void initview()
    {
        c=this;

        autocomplete_textview=(AutoCompleteTextView)findViewById(R.id.search_autocompletetext);
        recyclerView_search=(RecyclerView)findViewById(R.id.recycleview_search) ;
        gridLayoutManager = new GridLayoutManager(c, 2);

        setadapter_autocomplete();

        setadapter_recycleview();

    }

    private void setadapter_recycleview() {
        recyclerView_search.setLayoutManager(gridLayoutManager);

        for(int i=0;i<10;i++)

        {
            search_productlist.add(new CommomData("12233", "update_product_name", "", "http://administrator.aapkatrade.com/public/upload/220/nyc-pie-gurgaon-625_625x350_41460295362.jpg"));
        }
        commomAdapter = new CommomAdapter(c, search_productlist, "gridtype", "latestupdate");

        recyclerView_search.setAdapter(commomAdapter);





    }

    private void setadapter_autocomplete() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,language);
        autocomplete_textview.setAdapter(adapter);


    }


    private void call_search_suggest_webservice(String login_url,String input_txt) {

        // dialog.show();
        HashMap<String,String> webservice_body_parameter=new HashMap<>();
        webservice_body_parameter.put("authorization","xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        webservice_body_parameter.put("type","search");
        webservice_body_parameter.put("search_data",input_txt);



        HashMap<String,String> webservice_header_type=new HashMap<>();
        webservice_header_type.put("authorization","xvfdbgfdhbfdhtrh54654h54ygdgerwer3");





      //  Call_webservice.suggest_search(Search.this,login_url,"search",webservice_body_parameter,webservice_header_type);

        Call_webservice.taskCompleteReminder=new TaskCompleteReminder() {
            @Override
            public void Taskcomplete(JsonObject webservice_returndata) {

                Log.e("data2",webservice_returndata.toString());

                if (webservice_returndata != null)
                {
                    Log.e("webservice_returndata",webservice_returndata.toString());
                    JsonObject jsonObject = webservice_returndata.getAsJsonObject();

                    String error=jsonObject.get("error").getAsString();
                    String message=jsonObject.get("message").getAsString();
                    if(error.equals("false"))
                    {
                        product_ids.add("product123");
                        product_names.add("mobile cover");
                        product_ids.add("product123");
                        product_names.add("mobile cover");
                        product_ids.add("product123");
                        product_names.add("mobile cover");
                        product_ids.add("product123");
                        product_names.add("mobile cover");
                        product_ids.add("product123");
                        product_names.add("mobile cover");


                        categoryadapter = new CustomAutocompleteAdapter(c, product_ids, product_names);
                      //  searchsuggestionlist.setAdapter(categoryadapter);




//


                    }
                    else{
                        //showMessage(message);
                    }



                }


                autocomplete_textview.setAdapter(categoryadapter);

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
