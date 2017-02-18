package com.example.pat.aapkatrade.general.Utils;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.pat.aapkatrade.general.Validation;

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
}
