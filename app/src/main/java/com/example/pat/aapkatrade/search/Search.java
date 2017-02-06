package com.example.pat.aapkatrade.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Call_webservice;
import com.example.pat.aapkatrade.general.TaskCompleteReminder;
import com.example.pat.aapkatrade.user_dashboard.add_product.CustomSpinnerAdapter;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Search extends AppCompatActivity {
 AutoCompleteTextView autocomplete_textview;
    SearchResultsAdapter searchResultsAdapter;
    ListView searchsuggestionlist;
    ArrayList<String> product_ids=new ArrayList<>();
    ArrayList<String> product_names=new ArrayList<>();
    Context c;
    String[] language ={"C","C++","Java",".NET","iPhone","Android","ASP.NET","PHP"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        c=this;
        autocomplete_textview=(AutoCompleteTextView)findViewById(R.id.search_autocompletetext);
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


                        CustomSpinnerAdapter categoryadapter = new CustomSpinnerAdapter(c, product_ids, product_names);
                      //  searchsuggestionlist.setAdapter(categoryadapter);




//


                    }
                    else{
                        //showMessage(message);
                    }



                }

            }
        };






    }





}
