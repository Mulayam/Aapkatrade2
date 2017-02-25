package com.example.pat.aapkatrade.user_dashboard.order_list.cancel_order_fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.App_sharedpreference;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.user_dashboard.order_list.OrderListAdapter;
import com.example.pat.aapkatrade.user_dashboard.order_list.OrderListData;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;


public class CancelOrderFragment extends Fragment
{


    ArrayList<OrderListData> orderListDatas = new ArrayList<>();
    RecyclerView order_list;
    OrderListAdapter orderListAdapter;
    ProgressBarHandler progress_handler;
    LinearLayout layout_container;
    App_sharedpreference app_sharedpreference;
    String user_id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cancel_order, container, false);

        progress_handler = new ProgressBarHandler(getActivity());

        app_sharedpreference = new App_sharedpreference(getActivity());

        user_id = app_sharedpreference.getsharedpref("userid","");

        setup_layout(view);

        get_web_data();

        return view;
    }



    private void setup_layout(View view)
    {
        layout_container = (LinearLayout) view.findViewById(R.id.layout_container);

        order_list = (RecyclerView) view.findViewById(R.id.order_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        order_list.setLayoutManager(mLayoutManager);
    }


    private void get_web_data()
    {
        // layout_container.setVisibility(View.INVISIBLE);
        orderListDatas.clear();
        progress_handler.show();

        Ion.with(getActivity())
                .load("http://aapkatrade.com/slim/seller_order_list")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("seller_id","3")
                .setBodyParameter("type","1")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>()
                {
                    @Override
                    public void onCompleted(Exception e, JsonObject result)
                    {
                        if(result == null)
                        {
                            progress_handler.hide();
                            layout_container.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            JsonObject jsonObject = result.getAsJsonObject();

                            String message = jsonObject.get("message").toString().substring(0,jsonObject.get("message").toString().length());

                            String message_data = message.replace("\"", "");

                            System.out.println("message_data=================="+message_data);

                            if (message_data.toString().equals("No record found"))
                            {
                                progress_handler.hide();
                                layout_container.setVisibility(View.INVISIBLE);
                            }
                            else
                            {
                                JsonObject jsonObject1 = jsonObject.getAsJsonObject("result");

                                System.out.println("jsonOblect-------------"+jsonObject1.toString());

                                JsonArray jsonArray = jsonObject1.getAsJsonArray("list");

                                for (int i = 0; i < jsonArray.size(); i++)
                                {
                                    JsonObject jsonObject2 = (JsonObject) jsonArray.get(i);

                                    String order_id = jsonObject2.get("id").getAsString();

                                    String product_name= jsonObject2.get("product_name").getAsString();

                                    String product_price = jsonObject2.get("product_price").getAsString();

                                    String product_qty= jsonObject2.get("product_qty").getAsString();

                                    String address = jsonObject2.get("address").getAsString();

                                    String email = jsonObject2.get("email").getAsString();

                                    String buyersmobile = jsonObject2.get("buyersmobile").getAsString();

                                    String buyersname = jsonObject2.get("buyersname").getAsString();

                                    String  company_name = jsonObject2.get("cname").getAsString();

                                    String status = jsonObject2.get("status").getAsString();

                                    String created_at = jsonObject2.get("created_at").getAsString();

                                    orderListDatas.add(new OrderListData(order_id, product_name, product_price,product_qty,address,email,buyersmobile,buyersname,company_name,status,created_at));


                                }

                                orderListAdapter = new OrderListAdapter(getActivity(), orderListDatas);
                                order_list.setAdapter(orderListAdapter);
                                orderListAdapter.notifyDataSetChanged();
                                progress_handler.hide();
                            }

                            //   layout_container.setVisibility(View.VISIBLE);
                        }

                    }
                });
    }


}