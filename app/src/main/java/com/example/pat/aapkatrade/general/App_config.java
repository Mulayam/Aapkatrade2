package com.example.pat.aapkatrade.general;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

/**
 * Created by PPC21 on 18-Jan-17.
 */

public class App_config extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


    }

    public static void set_defaultfont(Context c) {
        TypefaceUtil.overrideFont(c, "SERIF", "OpenSans_Regular.ttf");


    }

    public static void setLocaleFa(Context context, String language){
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getApplicationContext().getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());


    }



}