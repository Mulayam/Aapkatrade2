package com.example.pat.aapkatrade.general;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.provider.Settings.Secure;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Locale;

/**
 * Created by PPC21 on 18-Jan-17.
 */

public class App_config extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

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

    public static String getCurrentDeviceId(Context context){
        return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }




}