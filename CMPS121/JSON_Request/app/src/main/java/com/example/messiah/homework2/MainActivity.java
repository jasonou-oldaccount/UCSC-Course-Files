package com.example.messiah.homework2;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Date;

import javax.xml.transform.ErrorListener;

public class MainActivity extends AppCompatActivity {

    // TextViews for all of the values we want to update
    TextView city;
    TextView temperature;
    TextView relativeHumidity;
    TextView windSpeedAverage;
    TextView windSpeedGusts;
    TextView weather;
    TextView elevation;
    TextView state;
    TextView dateView;

    // Button to get weather
    Button getWeatherButton;

    // request queue
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Used for toasts
        final Context context = getApplicationContext();

        // gets the button id
        getWeatherButton = (Button) findViewById(R.id.getWeather);

        // gets the TextView ids
        city = (TextView) findViewById(R.id.cityName);
        temperature = (TextView) findViewById(R.id.temperature);
        relativeHumidity = (TextView) findViewById(R.id.relativeHumidity);
        windSpeedAverage = (TextView) findViewById(R.id.windSpeedAverage);
        windSpeedGusts = (TextView) findViewById(R.id.windSpeedGusts);
        weather = (TextView) findViewById(R.id.weather);
        elevation = (TextView) findViewById(R.id.elevation);
        state = (TextView) findViewById(R.id.stateName);
        dateView = (TextView) findViewById(R.id.date);

        // used for volley
        requestQueue = Volley.newRequestQueue(this);

        // initial notification for user
        Toast.makeText(context, "Click Button to get Weather", Toast.LENGTH_SHORT).show();

        // checks for if user clicks on button to request data
        getWeatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // JSON Object Request
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://luca-teaching.appspot.com/weather/default/get_weather", null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // checks if there is a response from the server
                                try {
                                    // response object
                                    JSONObject responseObject = response.getJSONObject("response");

                                    // checks for  if the status of the server is error, if error, exit
                                    String result = responseObject.getString("result");
                                    if (result.equals("error")) {
                                        Toast.makeText(context, "Status(1): Status: error, please try again", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    // if no error, then connect and grab data
                                    Toast.makeText(context, "Status(0): Connected, getting data", Toast.LENGTH_SHORT).show();
                                    // conditions object
                                    JSONObject conditionObject = responseObject.getJSONObject("conditions");
                                    // observation_location object
                                    JSONObject observationLocationObject = conditionObject.getJSONObject("observation_location");

                                    dateView.setText(DateUtils.formatDateTime(context, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_12HOUR));

                                    String cityStatus = observationLocationObject.getString("city");
                                    city.setText(cityStatus);

                                    // gets elevation
                                    String elevationStatus = observationLocationObject.getString("elevation");
                                    elevation.setText("Elevation\n\n" + elevationStatus);

                                    // gets state
                                    String stateStatus = observationLocationObject.getString("state");
                                    state.setText(stateStatus);

                                    // gets humidity
                                    String relativeHumidityStatus = conditionObject.getString("relative_humidity");
                                    relativeHumidity.setText("Humidity\n\n" + relativeHumidityStatus);

                                    // gets weather
                                    String weatherStatus = conditionObject.getString("weather");
                                    weather.setText(weatherStatus);

                                    // gets wind mph
                                    String windAvgStatus = conditionObject.getString("wind_mph");
                                    windSpeedAverage.setText("Wing Speeds\n\n" + windAvgStatus +" MPH");

                                    // gets gust
                                    String windGustStatus = conditionObject.getString("wind_gust_mph");
                                    windSpeedGusts.setText("Gusts\n\n" + windGustStatus + " MPH");

                                    // gets temperature
                                    String temperatureStatus = conditionObject.getString("temp_f");
                                    temperature.setText(temperatureStatus + (char)0x00B0 + "F");

                                    // tells the user complete
                                    Toast.makeText(context, "Status(0): Data request complete", Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    // if no response, toasts a message to user
                                    Toast.makeText(context, "Status(1): Error 500, Server Failed", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, "Status(1): Error 500, Server Failed, please try again", Toast.LENGTH_SHORT).show();
                            }
                        });
                // requests data
                requestQueue.add(jsonObjectRequest);
            }
        });
    }
}
