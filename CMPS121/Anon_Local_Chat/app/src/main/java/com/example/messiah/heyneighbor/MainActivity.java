package com.example.messiah.heyneighbor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static String nickname = null;

    LocationManager lm;

    boolean gps_enabled = false;
    boolean network_enabled = false;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    }

    public void startChat(View view) {
        EditText nickname_field = (EditText) findViewById(R.id.nickname_field);
        nickname = nickname_field.getText().toString();

        checkLocationStatus();

        if(nickname.length() != 0 && gps_enabled && network_enabled) {
            startActivity(new Intent("com.example.messiah.heyneighbor.ChatActivity"));
            finish();
        }
    }

    public void checkLocationStatus() {
        try { gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER); } catch(Exception ex) {}
        try { network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER); } catch(Exception ex) {}

        if(!gps_enabled) Toast.makeText(context, "GPS not enabled", Toast.LENGTH_SHORT).show();
        if(!network_enabled) Toast.makeText(context, "Network not enabled", Toast.LENGTH_SHORT).show();
    }
}
