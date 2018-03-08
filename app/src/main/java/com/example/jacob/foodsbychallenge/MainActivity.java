package com.example.jacob.foodsbychallenge;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

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
        setDeliveriesInView(day);
    }

    public void onMonClick(View view) {
        setDeliveriesInView(Calendar.MONDAY);
    }

    public void onTueClick(View view) {
        setDeliveriesInView(Calendar.TUESDAY);
    }

    public void onWedClick(View view) {
        setDeliveriesInView(Calendar.WEDNESDAY);
    }

    public void onThuClick(View view) {
        setDeliveriesInView(Calendar.THURSDAY);
    }

    public void onFriClick(View view) {
        setDeliveriesInView(Calendar.FRIDAY);
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

        //Clear the parentLayout of any current items
        parentLayout.removeAllViews();

        //Populate the view
        for (Model.DropOff dropOff : model.dropOffs) {
            if (dropOff.day.equals(dayOfWeek)) {
                deliveries = dropOff.deliveries;

                for (int i = 0; i < deliveries.size(); i++) {
                    Model.Delivery delivery = deliveries.get(i);
                    TransitionManager.beginDelayedTransition(parentLayout);
                    getLayoutInflater().inflate(R.layout.delivery, parentLayout);
                    ConstraintLayout myView = (ConstraintLayout) parentLayout.getChildAt(i);

                    ImageView restIcon = (ImageView) myView.findViewById(R.id.restIcon);
                    TextView restName = (TextView) myView.findViewById(R.id.restName);
                    TextView orderBy = (TextView) myView.findViewById(R.id.orderBy);
                    TextView deliveryTime = (TextView) myView.findViewById(R.id.delivTime);
                    TextView deliveryStatus = (TextView) myView.findViewById(R.id.orderStatus);

                    Picasso.with(this).load(delivery.logoUrl).resize(500,400).into(restIcon);
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
