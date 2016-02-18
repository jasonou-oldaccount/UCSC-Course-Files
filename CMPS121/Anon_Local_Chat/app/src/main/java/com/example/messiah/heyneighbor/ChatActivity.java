package com.example.messiah.heyneighbor;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class ChatActivity extends Activity {

    LocationListener locationListener;
    LocationManager locationManager;

    boolean gps_enabled = false;
    boolean network_enabled = false;
    boolean location_found = false;

    final String BASE_URL_GET = "https://luca-teaching.appspot.com/localmessages/default/get_messages?";
    final String BASE_URL_POST = "https://luca-teaching.appspot.com/localmessages/default/post_message?";

    float latitude;
    float longitude;

    int backButtonCount = 0;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        context = getApplicationContext();

        TextView action_bar = (TextView) findViewById(R.id.chat_action_bar);
        action_bar.setText("Chatting as " + MainActivity.nickname.toUpperCase());

        if(!location_found) Toast.makeText(context, "Finding location... please wait", Toast.LENGTH_SHORT).show();

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        /**
         * Listens to the location, and gets the most precise recent location.
         */
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = (float) location.getLatitude();
                longitude = (float) location.getLongitude();
                location_found = true;
                Toast.makeText(context, "Location Found", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) { }

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    @Override
    public void onBackPressed() {
        if(backButtonCount >= 1) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            backButtonCount = 0;
            startActivity(intent);
        } else {
            Toast.makeText(context, "Press the back button again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.removeUpdates(locationListener);
    }

    public void sendMessage(View view) {
        checkLocationStatus();
        if(!gps_enabled || !network_enabled || !location_found) return;

        EditText messageToSend = (EditText) findViewById(R.id.message_to_send);
        String message = messageToSend.getText().toString();

        String message_id = "";
        Random rnd = new Random();
        String randomLetters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int n = 0; n < 10; ++n) message_id += randomLetters.charAt(rnd.nextInt(randomLetters.length()));

        System.out.println(message_id);
        System.out.println(latitude + ":" + longitude);

        if(message != "") {
            messageToSend.setText("");
        }
    }

    public void checkLocationStatus() {
        try { gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER); } catch(Exception ex) {}
        try { network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER); } catch(Exception ex) {}

        if(!gps_enabled) Toast.makeText(context, "GPS not enabled", Toast.LENGTH_SHORT).show();
        if(!network_enabled) Toast.makeText(context, "Network not enabled", Toast.LENGTH_SHORT).show();
    }
}
