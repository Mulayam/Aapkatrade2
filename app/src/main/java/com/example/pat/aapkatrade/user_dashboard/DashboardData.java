package com.example.pat.aapkatrade.user_dashboard;

/**
 * Created by PPC16 on 10-Jan-17.
 */

public class DashboardData
{

    String dashboard_id, dashboard_name;
    int image,color;

    DashboardData(String dashboard_id, String dashboard_name, int image,int color )
    {
        this.dashboard_id = dashboard_id;
        this.dashboard_name = dashboard_name;
        this.image = image;
        this.color= color;
    }
}
