package com.example.pat.aapkatrade.map;

import android.content.IntentSender;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pat.aapkatrade.general.CheckPermission;
import com.example.pat.aapkatrade.general.LocationManager_check;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.user_dashboard.add_product.AddProductActivity;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.androidannotations.annotations.rest.Get;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GoogleMapActivity extends AppCompatActivity implements OnMapReadyCallback {


    LatLng currentLatLng;
    public GoogleMap mMap;
    public LocationManager locationManager;
    Context context;

    public Polyline newPolyline;
    LatLng product_location_lat_lng, latLng;
    Button search, done;
    ImageView img_view_travelmode_car, img_view_travelmode_bus, img_view_travelmode_walking;

    ProgressBarHandler pg_handler;
    String address = "Address not found";
    Marker marker;
    Toolbar toolbar;
    private PlaceAutocompleteFragment autocompleteFragment;
    String TAG = "google_place";
    private float zoomLevel = 10;
    private LocationManager mLocationManager;
    String address_name;
    String drivingmode;
    LinearLayout location_container;
    double source_latitute, source_longitude;

    boolean permission_status;
    public static TextView tv_travel_duration, travel_time;
    GMapV2Direction md;
    RelativeLayout location_car_selected,location_bus_selected,location_walking_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        MapFragment googleMap = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        googleMap.getMapAsync(GoogleMapActivity.this);

        initview();
        setupToolBar();

        call_location_suggestion();


        //done = (Button) findViewById(R.id.done_button);


        location_container = (LinearLayout) findViewById(R.id.location_container);
        location_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    pg_handler.show();
                    Intent intent =
                            new PlaceAutocomplete
                                    .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(GoogleMapActivity.this);
                    startActivityForResult(intent, 1);


                } catch (GooglePlayServicesRepairableException e) {

                    pg_handler.hide();
                    Log.e("GooglePlayServices", e.toString());
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    pg_handler.hide();
                    Log.e("GooglePlayServices_not", e.toString());
                    // TODO: Handle the error.
                }


            }
        });
    }

    public void initview() {
        context = this;
        pg_handler=new ProgressBarHandler(this);

        md = new GMapV2Direction();
        String product_location = getIntent().getStringExtra("product_location");
        product_location_lat_lng = getLocationFromAddress(GoogleMapActivity.this, product_location);
        location_walking_selected=(RelativeLayout)findViewById(R.id.location_walking_selected);
        location_car_selected=(RelativeLayout)findViewById(R.id.location_car_selected);
        location_bus_selected=(RelativeLayout)findViewById(R.id.location_bus_selected);
        img_view_travelmode_car = (ImageView) findViewById(R.id.img_view_travelmode_car);
        img_view_travelmode_bus = (ImageView) findViewById(R.id.img_view_travelmode_bus);
        img_view_travelmode_walking = (ImageView) findViewById(R.id.img_view_travelmode_walking);
        tv_travel_duration = (TextView) findViewById(R.id.tv_travel_duration);
        travel_time = (TextView) findViewById(R.id.travel_time);
        init_location_elements("driving");


        img_view_travelmode_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_view_travelmode_car.setVisibility(View.GONE);
                location_car_selected.setVisibility(View.VISIBLE);
                location_bus_selected.setVisibility(View.GONE);
                location_walking_selected.setVisibility(View.GONE);
                img_view_travelmode_bus.setVisibility(View.VISIBLE);
                img_view_travelmode_walking.setVisibility(View.VISIBLE);
//                img_view_travelmode_car.setImageResource(R.drawable.ic_location_car_white);
                img_view_travelmode_walking.setImageResource(R.drawable.ic_location_walking_white);
                img_view_travelmode_bus.setImageResource(R.drawable.ic_location_bus_white);
                init_location_elements("driving");
            }
        });


        img_view_travelmode_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_view_travelmode_bus.setVisibility(View.GONE);
                img_view_travelmode_car.setVisibility(View.VISIBLE);
                img_view_travelmode_walking.setVisibility(View.VISIBLE);

                location_bus_selected.setVisibility(View.VISIBLE);
                location_car_selected.setVisibility(View.GONE);
                //img_view_travelmode_bus.setVisibility();

                location_walking_selected.setVisibility(View.GONE);

                img_view_travelmode_car.setImageResource(R.drawable.ic_location_car_white);
                img_view_travelmode_walking.setImageResource(R.drawable.ic_location_walking_white);
                img_view_travelmode_bus.setImageResource(R.drawable.ic_location_bus_white);
                init_location_elements("transit");


            }
        });

        img_view_travelmode_walking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_view_travelmode_walking.setVisibility(View.GONE);
                location_car_selected.setVisibility(View.GONE);
                location_bus_selected.setVisibility(View.GONE);
                location_walking_selected.setVisibility(View.VISIBLE);
                img_view_travelmode_car.setVisibility(View.VISIBLE);
                img_view_travelmode_bus.setVisibility(View.VISIBLE);
               // location_walking_selected.setVisibility(View.GONE);



                img_view_travelmode_car.setImageResource(R.drawable.ic_location_car_white);
                img_view_travelmode_walking.setImageResource(R.drawable.ic_location_walking_white);
                img_view_travelmode_bus.setImageResource(R.drawable.ic_location_bus_white);
                init_location_elements("walking");
            }
        });


    }

    private void init_location_elements(String Travelmode) {




        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        permission_status = CheckPermission.checkPermissions(GoogleMapActivity.this);
        if (permission_status) {
            LocationManager_check locationManagerCheck = new LocationManager_check(
                    this);
            Location location = null;

            if (locationManagerCheck.isLocationServiceAvailable()) {
                if (locationManagerCheck.getProviderType() == 1)
                    location = locationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                else if (locationManagerCheck.getProviderType() == 2)
                    location = locationManager
                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location location) {
                        source_latitute = location.getLatitude();
                        source_longitude = location.getLongitude();
                        LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                        Log.e("source+source_longitude",source_longitude+"***"+source_latitute);

                        ArrayList<LatLng> currenttoproduct = new ArrayList<>();
                        currenttoproduct.add(loc);
                        currenttoproduct.add(product_location_lat_lng);

                        // handleGetDirectionsResult(currenttoproduct);
                        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
                    }
                };
//                mMap.setOnMyLocationChangeListener(myLocationChangeListener);
//
//
//                mMap.getUiSettings().setRotateGesturesEnabled(true);
if(location!=null) {
    findDirections(location.getLatitude(), location.getLongitude(), product_location_lat_lng.latitude, product_location_lat_lng.longitude, Travelmode);
}
                else{
    Log.e("location not found","location not found");

}

            } else {
                locationManagerCheck.createLocationServiceError(GoogleMapActivity.this);
            }


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.


                return;
            }


        }


    }


    private void call_location_suggestion() {

//        autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
//
//        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//
//            @Override
//            public void onPlaceSelected(Place place) {
//                Log.i(TAG, "Place: " + place.getName());
//
//                Log.e("Address data----------", place.toString());
//
//                try {
//                    try {
//                        marker.remove();
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                    marker = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getAddress().toString()));
//                    currentLatLng = place.getLatLng();
//                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), zoomLevel));
//                    address = place.getAddress().toString();
//
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onError(Status status) {
//                // TODO: Handle the error.
//                Log.i(TAG, "An error occurred: " + status);
//            }
//        });


    }


    private void showMessage(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    private void setupToolBar(String app_name) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(app_name);

    }


    public void onMapReady(GoogleMap map) {

        mMap = map;


        search = (Button) findViewById(R.id.search_button);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);


    }


    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        try {
            mLocationManager.removeUpdates(mLocationListener);
            mLocationManager = null;

        } catch (Exception ex) {

        }
    }

    private void setupToolBar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar_map);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);

        // getSupportActionBar().setIcon(R.drawable.logo_word);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.empty_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }








    private final LocationListener mLocationListener = new LocationListener()
    {
        @Override
        public void onLocationChanged(final Location location)
        {
            //your code here
            try {
                marker.remove();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            marker = mMap.addMarker(new MarkerOptions().position(latLng).title(getString(R.string.selected_place)));
            currentLatLng = latLng;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
            zoomLevel = 18;
        //    setCompleteAddress(latLng);
            mMap.setMyLocationEnabled(true);

            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            try {
                mLocationManager.removeUpdates(mLocationListener);
                mLocationManager = null;

            } catch (Exception ex) {
                Log.e("Exception_ex",ex.toString());

            }


        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };




    private void setCompleteAddress(LatLng latLng) {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        StringBuilder completeAddress = new StringBuilder();
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (Exception ex) {
            return;
        }
        try {
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            completeAddress.append(address);
        } catch (Exception ex) {
            completeAddress.append("");
        }
        try {
            String city = addresses.get(0).getLocality();
            completeAddress.append(city);

        } catch (Exception ex) {
            completeAddress.append("");

        }
        try {
            String state = addresses.get(0).getAdminArea();
            completeAddress.append(state);
        } catch (Exception ex) {
            completeAddress.append("");
        }
        try {
            String knownName = addresses.get(0).getFeatureName();
            completeAddress.append(knownName);
        } catch (Exception ex) {
            completeAddress.append("");
        }
        try {
            marker.remove();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        marker = mMap.addMarker(new MarkerOptions().position(latLng).title(completeAddress.toString()));
        currentLatLng = latLng;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
        GoogleMapActivity.this.address = completeAddress.toString();
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            android.location.Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }


    private void clearMarker() {
        try {
            mMap.clear();
        } catch (Exception ex) {

        }
    }




    public void findDirections(double fromPositionDoubleLat, double fromPositionDoubleLong, double toPositionDoubleLat, double toPositionDoubleLong, String mode)
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put(GetDirectionsAsyncTask4.USER_CURRENT_LAT,
                String.valueOf(fromPositionDoubleLat));
        map.put(GetDirectionsAsyncTask4.USER_CURRENT_LONG,
                String.valueOf(fromPositionDoubleLong));
        map.put(GetDirectionsAsyncTask4.DESTINATION_LAT,
                String.valueOf(toPositionDoubleLat));
        map.put(GetDirectionsAsyncTask4.DESTINATION_LONG,
                String.valueOf(toPositionDoubleLong));
        map.put(GetDirectionsAsyncTask4.DIRECTIONS_MODE, mode);

        GetDirectionsAsyncTask4 asyncTask = new GetDirectionsAsyncTask4(GoogleMapActivity.this);
        asyncTask.execute(map);


    }

    public void handleGetDirectionsResult(ArrayList<LatLng> directionPoints)
    {
        PolylineOptions rectLine = new PolylineOptions().width(10).color(R.color.polyline_color);

        for (int i = 0; i < directionPoints.size(); i++) {
            rectLine.add(directionPoints.get(i));
        }

        mMap.clear();
        if (newPolyline != null) {
            newPolyline.remove();
        }

        newPolyline = mMap.addPolyline(rectLine);

        MarkerOptions marker2 = new MarkerOptions().position(
                new LatLng(directionPoints.get(0).latitude, directionPoints.get(0).longitude)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_my_location_icon)).title(
                "My Location");

        MarkerOptions marker3 = new MarkerOptions().position(
                new LatLng(directionPoints.get(directionPoints.size()-1).latitude,directionPoints.get(directionPoints.size()-1).longitude)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_product_location)).title(
                "Product location");

        mMap.addMarker(marker2);
        mMap.addMarker(marker3);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(directionPoints.get(0).latitude, directionPoints.get(0).longitude), 10.0f));

        String  distance=md.getDistanceText(GetDirectionsAsyncTask4.doc);
        String  travel_duration=md.getDurationText(GetDirectionsAsyncTask4.doc);
        tv_travel_duration.setText(travel_duration);
        travel_time.setText("("+distance+")");


    }



    public interface AddressListner {
        void gotAddress(String address, boolean source);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                pg_handler.hide();
                // retrive the data by using getPlace() method.
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.e("Tag", "Place: " + place.getAddress()  +place.getLatLng());
                Geocoder mGeocoder = new Geocoder(GoogleMapActivity.this, Locale.getDefault());


                findDirections(place.getLatLng().latitude,place.getLatLng().longitude, product_location_lat_lng.latitude, product_location_lat_lng.longitude, "driving");



                //init_location_elements("driving");
//                List<Address> addresses = null;
//
//
//                    String cityName = addresses.get(0).getAddressLine(0);
//                    String stateName = addresses.get(0).getAddressLine(1);
//                    String countryName = addresses.get(0).getAddressLine(2);
//                    String countryName2 = addresses.get(0).getAddressLine(3);
//
//
//                    Log.e("cityName",cityName);
//                    Log.e("stateName",stateName);
//                    Log.e("countryName",countryName);
//                    Log.e("countryName2",countryName2);
              //  "place.getName()"+"",\n"+ place.getAddress()"



                    ///addresses = mGeocoder.getFromLocation(place.getLatLng().latitude,place.getLatLng().longitude, 1);

              //  findDirections(source_latitute,place.getLatLng().latitude,source_longitude,place.getLatLng().longitude,"WALKING");
//                String url = "https://maps.googleapis.com/maps/api/directions/json?";
//                url=url+"origin="+place.getLatLng().latitude+","+place.getLatLng().longitude+"&"+"destination="+source_latitute+","+source_longitude+"&key="+getResources().getString(R.string.google_api);
//
//                Log.d("Result",url);
//
//                Ion.with(this).load(url)
//                        .asJsonObject()
//                        .setCallback(new FutureCallback<JsonObject>() {
//                            @Override
//                            public void onCompleted(Exception e, JsonObject result) {
//
//                                Log.e("route_Result", String.valueOf(result));
//
//                                if(result!=null){
//
//
//                                    JsonArray ja= result.getAsJsonArray("routes");
//
//
//                                    for( int i=0; i<ja.size();i++){
//
//                                        JsonObject rs= ja.get(i).getAsJsonObject();
//
//                                        String name= rs.get("copyrights").getAsString();
//
//                                        Log.d("Result",name);
//
//                                    }
//
//
//
//                                }else{
//
//                                }
//
//                            }
//                        });







            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                pg_handler.hide();
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.e("Tag", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                pg_handler.hide();
                // The user canceled the operation.
            }
        }
    }






























}
