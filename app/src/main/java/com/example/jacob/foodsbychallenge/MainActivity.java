package com.example.jacob.foodsbychallenge;

import android.support.annotation.StringRes;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Model model;
    int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String json = readDelivieriesJSON();
        model = new GsonBuilder().create().fromJson(json, Model.class);

        setTodayButton();
        setDeliveriesInView(day + 1);
    }

    private void setTodayButton() {
        Calendar calender = Calendar.getInstance();
        day = calender.get(Calendar.DAY_OF_WEEK);
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

    private void setDeliveriesInView(int day) {
        String dayOfWeek = "";
        switch (day) {
            case Calendar.MONDAY:
                dayOfWeek = "Monday";
                break;
            case Calendar.TUESDAY:
                dayOfWeek = "Tuesday";
                break;
            case Calendar.WEDNESDAY:
                dayOfWeek = "Wednesday";
                break;
            case Calendar.THURSDAY:
                dayOfWeek = "Thursday";
                break;
            case Calendar.FRIDAY:
                dayOfWeek = "Friday";
                break;
            default:
                break;
        }
        List<Model.Delivery> deliveries;
        LinearLayout parentLayout = findViewById(R.id.places);

        for (Model.DropOff dropOff : model.dropOffs) {
            if (dropOff.day.equals(dayOfWeek)) {
                deliveries = dropOff.deliveries;

                for (int i = 0; i < deliveries.size(); i++) {
                    Model.Delivery delivery = deliveries.get(i);
                    getLayoutInflater().inflate(R.layout.delivery, parentLayout);
                    ConstraintLayout view1 = (ConstraintLayout) parentLayout.getChildAt(i);
                    LinearLayout myView = (LinearLayout) view1.getChildAt(0);

                    ImageView restIcon = (ImageView) myView.findViewById(R.id.restIcon);
                    TextView restName = (TextView) myView.findViewById(R.id.restName);
                    TextView orderBy = (TextView) myView.findViewById(R.id.orderBy);
                    TextView deliveryTime = (TextView) myView.findViewById(R.id.delivTime);
                    TextView deliveryStatus = (TextView) myView.findViewById(R.id.orderStatus);

                    restName.setText(delivery.restaurantName);
                    orderBy.setText(delivery.cutoff);
                    deliveryTime.setText(delivery.dropoff);
                    //todo implement delivery status based on boolean values in delivery
                }


                break;
            }
        }
    }


}
