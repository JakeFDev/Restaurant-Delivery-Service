package com.example.jacob.foodsbychallenge;

import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
