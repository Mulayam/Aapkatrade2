package com.example.pat.aapkatrade.general;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by PPC21 on 30-Jan-17.
 */

public class Tabletsize {

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}
