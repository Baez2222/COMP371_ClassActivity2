package com.example.android.classactivity2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private TextView textView_displayCity;
    private TextView textView_descr;
    private TextView textView_high;
    private TextView textView_highNumber;
    private TextView textView_low;
    private TextView textView_lowNumber;
    private TextView textView_feels;
    private TextView textView_feelsNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if(intent.getStringExtra("none") == null){
            setContentView(R.layout.activity_second);

            textView_displayCity = findViewById(R.id.textView_displayCity);
            textView_descr = findViewById(R.id.textView_descr);
            textView_high = findViewById(R.id.textView_high);
            textView_highNumber = findViewById(R.id.textView_highNumber);
            textView_low = findViewById(R.id.textView_low);
            textView_lowNumber = findViewById(R.id.textView_lowNumber);
            textView_feels = findViewById(R.id.textView_feels);
            textView_feelsNumber = findViewById(R.id.textView_feelsNumber);


            textView_displayCity.setText(intent.getStringExtra("city") + ", " + intent.getStringExtra("country"));
            textView_descr.setText(intent.getStringExtra("weather_descr"));
            textView_highNumber.setText(intent.getStringExtra("highest_temp") + "F");
            textView_lowNumber.setText(intent.getStringExtra("lowest_temp") + "F");
            textView_feelsNumber.setText(intent.getStringExtra("feels_like") + "F");
        }
        else{
            setContentView(R.layout.activity_error);
        }

    }
}
