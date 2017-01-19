package com.example.pat.aapkatrade.general;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by abcd on 10/6/2016.
 */
public class ConnetivityCheck {


    public static boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
