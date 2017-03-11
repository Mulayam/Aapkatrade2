package com.example.pat.aapkatrade.user_dashboard.service_enquirylist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.App_sharedpreference;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.user_dashboard.companylist.CompanyList;
import com.example.pat.aapkatrade.user_dashboard.companylist.CompanyListHolder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by PPC16 on 10-Mar-17.
 */

public class ServiceEnquiryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    final LayoutInflater inflater;
    List<ServiceEnquiryData> itemList;
    Context context;
    CompanyListHolder viewHolder;
    ServiceEnquiryActivity  serviceEnquiryActivity;
    int p;
    App_sharedpreference app_sharedpreference;
    String email;
    ProgressBarHandler progress_handler;


    public ServiceEnquiryAdapter(Context context, List<ServiceEnquiryData> itemList,ServiceEnquiryActivity serviceEnquiryActivity)
    {

        this.serviceEnquiryActivity = serviceEnquiryActivity;
        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        app_sharedpreference = new App_sharedpreference(context);
        progress_handler = new ProgressBarHandler(context);

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.row_service_enquiry, parent, false);

        viewHolder = new CompanyListHolder(view);

        System.out.println("data-----------"+itemList);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {

        final CompanyListHolder homeHolder = (CompanyListHolder) holder;

      /*  homeHolder.tvCompanyname.setText(itemList.get(position).company_name);

        email = app_sharedpreference.getsharedpref("emailid", "");

        homeHolder.tvEmail.setText(email);

        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = null;

        try
        {
            date = form.parse(itemList.get(position).company_creation_date);

            System.out.println("datae----------"+itemList.get(position).company_creation_date);

        }
        catch (ParseException e)
        {

            e.printStackTrace();
        }


        SimpleDateFormat postFormater = new SimpleDateFormat("MMMM dd, yyyy");
        String newDateStr = postFormater.format(date);

        homeHolder.tvDate.setText(newDateStr);
      */


    }

    @Override
    public int getItemCount()
    {

        return itemList.size();
    }

    public String getCurrentTimeStamp()
    {
        return new SimpleDateFormat("dd MMM yyyy HH:mm").format(new Date());
    }



}