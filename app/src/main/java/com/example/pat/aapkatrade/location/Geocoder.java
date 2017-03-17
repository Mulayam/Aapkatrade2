package com.example.pat.aapkatrade.location;

import android.content.Context;
import android.location.Address;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by PPC17 on 17-Mar-17.
 */

public class Geocoder {

    Context c;
    public double latitude, longitude;
    String result ;

    public Geocoder(Context c, double latitude, double longitude) {
        this.c = c;
        this.latitude = latitude;
        this.longitude = longitude;
        get_state_name();

    }


    public String get_state_name() {

        android.location.Geocoder geocoder = new android.location.Geocoder(c, Locale.getDefault());


        List<Address> addressList = null;
        try {
            addressList = geocoder.getFromLocation(
                    latitude, longitude, 1);

            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);

//
                result = address.getAdminArea();

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }






}
