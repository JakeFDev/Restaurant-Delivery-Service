package com.example.jacob.foodsbychallenge;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        resetAllButtons();
        Button mondayBtn = findViewById(R.id.mondayBtn);
        mondayBtn.setBackgroundResource(R.drawable.button_active);
        mondayBtn.setTextColor(Color.WHITE);
        mondayBtn.setElevation(100);
    }

    public void onTueClick(View view) {
        setDeliveriesInView(Calendar.TUESDAY);
        resetAllButtons();
        Button tuesBtn = findViewById(R.id.tuesdayBtn);
        tuesBtn.setBackgroundResource(R.drawable.button_active);
        tuesBtn.setTextColor(Color.WHITE);
        tuesBtn.setElevation(100);
    }

    public void onWedClick(View view) {
        setDeliveriesInView(Calendar.WEDNESDAY);
        resetAllButtons();
        Button wedBtn = findViewById(R.id.wednesdayBtn);
        wedBtn.setBackgroundResource(R.drawable.button_active);
        wedBtn.setTextColor(Color.WHITE);
        wedBtn.setElevation(100);
    }

    public void onThuClick(View view) {
        setDeliveriesInView(Calendar.THURSDAY);
        resetAllButtons();
        Button thuBtn = findViewById(R.id.thursdayBtn);
        thuBtn.setBackgroundResource(R.drawable.button_active);
        thuBtn.setTextColor(Color.WHITE);
        thuBtn.setElevation(100);
    }

    public void onFriClick(View view) {
        setDeliveriesInView(Calendar.FRIDAY);
        resetAllButtons();
        Button friBtn = findViewById(R.id.fridayBtn);
        friBtn.setBackgroundResource(R.drawable.button_active);
        friBtn.setTextColor(Color.WHITE);
        friBtn.setElevation(100);
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
        todayBtn.setBackgroundResource(R.drawable.button_active);
        todayBtn.setElevation(100);
        todayBtn.setTextColor(Color.WHITE);
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
                    ViewGroup view = (ViewGroup) getLayoutInflater().inflate(R.layout.delivery, parentLayout);
                    ConstraintLayout myView = (ConstraintLayout) parentLayout.getChildAt(i);

                    ImageView restIcon = (ImageView) myView.findViewById(R.id.restIcon);
                    TextView restName = (TextView) myView.findViewById(R.id.restName);
                    TextView orderBy = (TextView) myView.findViewById(R.id.orderBy);
                    TextView deliveryTime = (TextView) myView.findViewById(R.id.delivTime);
                    TextView deliveryStatus = (TextView) myView.findViewById(R.id.orderStatus);

                    DateFormat outputFormat = new SimpleDateFormat("hh:mm aa");
                    DateFormat inputFormat = new SimpleDateFormat("HH:mm:ss");
                    try {
                        Date dateCutoff = inputFormat.parse(delivery.cutoff);
                        Date dateDropoff = inputFormat.parse(delivery.dropoff);

                        String sDateCutoff = outputFormat.format(dateCutoff);
                        String sDateDropoff = outputFormat.format(dateDropoff);

                        orderBy.setText(sDateCutoff);
                        deliveryTime.setText(sDateDropoff);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Picasso.with(this).load(delivery.logoUrl).resize(500,400).into(restIcon);
                    restName.setText(delivery.restaurantName);

                    if (delivery.isOrderPlaced) {
                        deliveryStatus.setText(R.string.order_placed);
                    }
                    else if (delivery.soldOut) {
                        deliveryStatus.setText(R.string.sold_out);
                    }
                    else if (delivery.isPastCutoff) {
                        deliveryStatus.setText(R.string.is_past_cutoff);
                    }
                    else if (delivery.sellingOut) {
                        deliveryStatus.setText(R.string.order_placed);
                    }
                    else {
                        deliveryStatus.setVisibility(View.INVISIBLE);
                    }

                }
                break;
            }
        }
    }

    private void resetAllButtons() {
        LinearLayout buttonContainer = findViewById(R.id.buttonContainer);
        for (int i = 0; i < buttonContainer.getChildCount(); i++) {
            Button button = (Button) buttonContainer.getChildAt(i);
            button.setBackgroundResource(R.drawable.button_focus);
            button.setTextColor(Color.BLACK);
            button.setElevation(0);
        }

    }


}
