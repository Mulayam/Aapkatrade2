package com.example.pat.aapkatrade.search;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ListView;

import com.example.pat.aapkatrade.general.TaskCompleteReminder;

import org.json.JSONArray;

/**
 * Created by PPC21 on 06-Feb-17.
 */

public class search_async_task extends AsyncTask<String, Void, String>
{
    //JSONParser jParser;
    JSONArray productList;
    String url=new String();
    String textSearch;
    ProgressDialog pd;
    ListView product_suggestion;
    TaskCompleteReminder taskCompleteReminder=null;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        productList=new JSONArray();
       // jParser = new JSONParser();

    }

    @Override
    protected String doInBackground(String... sText) {

        url="http://lawgo.in/lawgo/products/user/1/search/"+sText[0];
        String returnResult = getProductList(url);
        this.textSearch = sText[0];


        return returnResult;

    }

    public String getProductList(String url)
    {


        //productResults is an arraylist with all product details for the search criteria
        //productResults.clear();

return "";

    }

    @Override
    protected void onPostExecute(String result) {

        super.onPostExecute(result);

        if(result.equalsIgnoreCase("Exception Caught"))
        {

        }
        else
        {


            //calling this method to filter the search results from productResults and move them to
            //filteredProductResults
          //  filterProductArray(textSearch);
            //searchResults.setAdapter(new SearchResultsAdapter(getActivity(),filteredProductResults));
            pd.dismiss();
        }
    }

}