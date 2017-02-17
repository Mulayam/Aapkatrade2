package com.example.pat.aapkatrade.map;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.maps.model.LatLng;
import org.w3c.dom.Document;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class GetDirectionsAsyncTask4 extends AsyncTask<Map<String, String>, Object, ArrayList<LatLng>> {


    public static final String USER_CURRENT_LAT = "user_current_lat";
    public static final String USER_CURRENT_LONG = "user_current_long";
    public static final String DESTINATION_LAT = "destination_lat";
    public static final String DESTINATION_LONG = "destination_long";
    public static final String DIRECTIONS_MODE = "directions_mode";
    private GoogleMapActivity activity;
    private Exception exception;
    private ProgressDialog progressDialog;
   public static String distance;
   public static String travel_duration;
    GMapV2Direction md = new GMapV2Direction();
 public static Document doc;
    public GetDirectionsAsyncTask4(GoogleMapActivity activity) {
        super();
        this.activity = activity;
    }

    public void onPreExecute() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Calculating directions");
        progressDialog.show();
    }

    @Override
    public void onPostExecute(ArrayList result) {
        progressDialog.dismiss();
        if (exception == null) {
            Log.e("result_location",result.toString());
            activity.handleGetDirectionsResult(result);
        } else {

            processException();
        }


    }

    @Override
    protected ArrayList<LatLng> doInBackground(Map<String, String>... params) {
        Map<String, String> paramMap = params[0];
        try {

            System.out.println("hi ===" + paramMap.get(USER_CURRENT_LAT));

            LatLng fromPosition = new LatLng(Double.valueOf(paramMap.get(USER_CURRENT_LAT)), Double.valueOf(paramMap.get(USER_CURRENT_LONG)));
            LatLng toPosition = new LatLng(Double.valueOf(paramMap.get(DESTINATION_LAT)), Double.valueOf(paramMap.get(DESTINATION_LONG)));



          doc = md.getDocument(fromPosition, toPosition, paramMap.get(DIRECTIONS_MODE),activity);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, result);
            String xmlString = sw.toString();




          distance=md.getDistanceText(doc);
           travel_duration=md.getDurationText(doc);

            Log.e("distance_doc",distance);
            Log.e("travel_duration",travel_duration);
            ArrayList<LatLng> directionPoints = md.getDirection(doc);


            return directionPoints;


        } catch (Exception e) {
            exception = e;
            return null;
        }
    }

    private void processException() {
        Log.e("process_exception","error_retriving_data");
        //Toast.makeText(activity, "Error retriving data", Toast.LENGTH_LONG).show();
    }


}
