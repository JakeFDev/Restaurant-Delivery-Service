package com.example.jacob.foodsbychallenge;

import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String json = readDelivieriesJSON();
        model = new GsonBuilder().create().fromJson(json, Model.class);

        setTodayButton();
    }

    private void setTodayButton() {
        Calendar calender = Calendar.getInstance();
        int day = calender.get(Calendar.DAY_OF_WEEK);
        Button todayBtn = findViewById(R.id.mondayBtn);
        switch (day) {
            case Calendar.MONDAY:
                todayBtn = findViewById(R.id.mondayBtn);
                break;
            case Calendar.TUESDAY:
                todayBtn = findViewById(R.id.tuesdayBtn);
                break;
            case Calendar.WEDNESDAY:
                todayBtn = findViewById(R.id.wednesdayBtn);
                break;
            case Calendar.THURSDAY:
                todayBtn = findViewById(R.id.thursdayBtn);
                break;
            case Calendar.FRIDAY:
                todayBtn = findViewById(R.id.fridayBtn);
                break;
            default:
                break;
        }
        todayBtn.setText(R.string.today);
    }

    public void PrintAllDeliveries(View v) {
        for (Model.Delivery d : model.dropOffs.get(0).deliveries) {
            System.out.println(d.toString());
        }

        for (Model.Delivery d : model.dropOffs.get(1).deliveries) {
            System.out.println(d.toString());
        }

        for (Model.Delivery d : model.dropOffs.get(2).deliveries) {
            System.out.println(d.toString());
        }

        for (Model.Delivery d : model.dropOffs.get(3).deliveries) {
            System.out.println(d.toString());
        }

        for (Model.Delivery d : model.dropOffs.get(4).deliveries) {
            System.out.println(d.toString());
        }
    }

    private String readDelivieriesJSON() {
        String json = null;
        try {
            InputStream is = this.getResources().openRawResource(R.raw.deliveries_sample);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Model", "Failed to open deliveries json file!");
            return null;
        }
        return json;
    }
}
