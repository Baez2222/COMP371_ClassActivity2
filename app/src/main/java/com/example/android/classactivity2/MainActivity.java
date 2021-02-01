package com.example.android.classactivity2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private Button button_go;
    private TextView textView_city;

    private static String base_url = "https://api.openweathermap.org/data/2.5/weather?q=";
    private static String end_url = "&units=imperial&appid=109793b96d6ce346c85ecc7957e252da";
    private static AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView_city = findViewById(R.id.textView_city);
        button_go = findViewById(R.id.button_go);
        // add click listener for button_go
        button_go.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                launchNextActivity(view);
            }
        });
    }

    public void launchNextActivity(View view){
        String city_name = textView_city.getText().toString();

        String api_url = base_url + city_name + end_url;

        client.get(api_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                // when you get a 200 status code
                Log.d("api response", new String(responseBody));
                try {
                    JSONObject json = new JSONObject(new String(responseBody));
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    // add weather data into the intent
                    intent.putExtra("city", json.getString("name"));

                    JSONObject sys = json.getJSONObject("sys");
                    intent.putExtra("country", sys.getString("country"));

                    JSONArray weatherArray = json.getJSONArray("weather");
                    intent.putExtra("weather_descr", weatherArray.getJSONObject(0).getString("description"));

                    JSONObject main = json.getJSONObject("main");
                    intent.putExtra("highest_temp", main.getString("temp_max"));
                    intent.putExtra("lowest_temp", main.getString("temp_min"));
                    intent.putExtra("feels_like", main.getString("feels_like"));

                    // convert any json data into a string to put into the intent
                    // when you receive the intent in the next activity,
                    // convert it back to the json data
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("api error", new String(responseBody));

                Intent intent = new Intent(MainActivity.this, SecondActivity.class);

                // add to intent no such city exists in API calls
                intent.putExtra("none", "none");
                startActivity(intent);
            }
        });
    }

}