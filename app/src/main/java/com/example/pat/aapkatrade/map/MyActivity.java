package com.example.pat.aapkatrade.map;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.pat.aapkatrade.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MyActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        // Getting reference to button btn_show
        Button btnShow = (Button) findViewById(R.id.btn_show);

        // Setting click event listener for the button
        btnShow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                double lat=0;
                double lng=0;

                // Getting reference to EditText et_lat
                EditText etLat = (EditText) findViewById(R.id.et_lat);

                // Validating User Input
                if(etLat.getText().toString().isEmpty()){
                    Toast.makeText(getBaseContext(), "Please Enter Latitude", Toast.LENGTH_SHORT).show();

                    // Focus the EditText for Latitude
                    etLat.requestFocus();

                    return;
                }else{
                    // Getting user input latitude
                    lat = Double.parseDouble(etLat.getText().toString());
                }

                // Getting reference to EditText et_lng
                EditText etLng = (EditText) findViewById(R.id.et_lng);

                // Validating User Input
                if(etLng.getText().toString().isEmpty()){
                    Toast.makeText(getBaseContext(), "Please Enter Longitude", Toast.LENGTH_SHORT).show();

                    // Focus the EditText for Longitude
                    etLng.requestFocus();

                    return;
                }else{
                    // Getting user input longitude
                    lng = Double.parseDouble(etLng.getText().toString());
                }

                Intent intent = new Intent(getBaseContext(), GoogleMapActivity.class);
                // Passing latitude and longitude to the MapActiv
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);

                startActivity(intent);
            }
        });

    }


}