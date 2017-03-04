package com.example.pat.aapkatrade.general.Utils;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.pat.aapkatrade.general.Validation;

import java.util.Calendar;

/**
 * Created by PPC09 on 03-Feb-17.
 */

public class AndroidUtils {
    public static void showSnackBar(ViewGroup layout, String message) {

        Snackbar snackbar = Snackbar
                .make(layout, message, Snackbar.LENGTH_SHORT)
                .setAction("", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
        snackbar.show();


    }


    public static String getEditTextData(EditText et){
        if(Validation.validateEdittext(et)){
            return et.getText().toString();
        }
        return "";
    }

    public static String getUserType(String user_type) {
        if(user_type.equals("1")){
            return "2";
        } else if(user_type.equals("2")){
            return "1";
        }
        return user_type;
    }

    public static Calendar stringToCalender(String date_yyyy_mm_dd){
        int day = Integer.parseInt(date_yyyy_mm_dd.split("-")[2]);
        int month = Integer.parseInt(date_yyyy_mm_dd.split("-")[1]);
        int year = Integer.parseInt(date_yyyy_mm_dd.split("-")[0]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month-1, day);
        return calendar;
    }


}
