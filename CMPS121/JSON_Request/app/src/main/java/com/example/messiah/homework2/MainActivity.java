package com.example.messiah.homework2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import javax.xml.transform.ErrorListener;

public class MainActivity extends AppCompatActivity {

    // TextViews for all of the values we want to update
    TextView city;
    TextView location;
    TextView temperature;
    TextView relativeHumidity;
    TextView windSpeedAverage;
    TextView windSpeedGusts;
    TextView weather;

    // Button to get weather
    Button getWeatherButton;

    // request queue
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWeatherButton = (Button) findViewById(R.id.getWeather);

        city = (TextView) findViewById(R.id.cityName);
        location = (TextView) findViewById(R.id.location);
        temperature = (TextView) findViewById(R.id.temperature);
        relativeHumidity = (TextView) findViewById(R.id.relativeHumidity);
        windSpeedAverage = (TextView) findViewById(R.id.windSpeedAverage);
        windSpeedGusts = (TextView) findViewById(R.id.windSpeedGusts);
        weather = (TextView) findViewById(R.id.weather);

        requestQueue = Volley.newRequestQueue(this);

        getWeatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("FAIL0");
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://luca-teaching.appspot.com/weather/default/get_weather", null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject("response");
                                    System.out.println(jsonObject.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println("FAIL");
                            }
                        });
                requestQueue.add(jsonObjectRequest);
            }
        });
    }
}
