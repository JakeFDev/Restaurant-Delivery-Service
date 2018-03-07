package com.example.jacob.foodsbychallenge;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacob on 3/7/18.
 */

public class Model {

    @SerializedName("dropoffs")
    List<DropOff> dropOffs;

    public class DropOff {
        @SerializedName("day")
        String day;

        @SerializedName("deliveries")
        List<Delivery> deliveries;
    }

    public class Delivery {
        @SerializedName("deliveryId")
        public int deliveryId;

        @SerializedName("storeId")
        public int storeId;

        @SerializedName("restaurantName")
        public String restaurantName;

        @SerializedName("logoUrl")
        public String logoUrl;

        @SerializedName("cutoff")
        public String cutoff;

        @SerializedName("dropoff")
        public String dropoff;

        @SerializedName("soldOut")
        public boolean soldOut;

        @SerializedName("sellingOut")
        public boolean sellingOut;

        @SerializedName("isPastCutoff")
        public boolean isPastCutoff;

        @SerializedName("isOrderPlaced")
        public boolean isOrderPlaced;

        public String toString() {
            return deliveryId + "\n" + storeId + "\n" + restaurantName + "\n" +
                    logoUrl + "\n" + cutoff + "\n" + dropoff + "\n" + soldOut + "\n" +
                    sellingOut + "\n" + isPastCutoff + "\n" + isOrderPlaced;
        }
    }

}
