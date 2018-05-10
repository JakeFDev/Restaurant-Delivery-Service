package com.example.jacob.foodsbychallenge;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by jacob on 4/30/18.
 */
public class DeliveryListAdapter extends ArrayAdapter {

    private final Activity context;
    private final Model.DropOff[] dropOffs;
    private final String dayOfWeek;

    public DeliveryListAdapter(Activity context, Object[] dropOffs, String dayOfWeek, String[] restNames) {
        super(context, R.layout.delivery_card, restNames);

        this.dropOffs = new Model.DropOff[dropOffs.length];
        for (int i = 0; i < dropOffs.length; i++) {
            this.dropOffs[i] = (Model.DropOff) dropOffs[i];
        }

        this.context = context;
        this.dayOfWeek = dayOfWeek;
    }

    public View getView(int position, View view, ViewGroup parent) {
        //Inflate a card
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.delivery_card, null, true);

        //Populate the view
        for (int i = 0; i < dropOffs.length; i++) {
            //Grab each dropOff in the array until we get the one for the day of week
            Model.DropOff dropOff = dropOffs[i];

            if (dropOff.day.equals(dayOfWeek)) {
                //Get the delivery for the specified position in the list view
                Model.Delivery delivery = dropOff.deliveries.get(position);

                ImageView restIcon = (ImageView) rowView.findViewById(R.id.restIcon);
                TextView restName = (TextView) rowView.findViewById(R.id.restName);
                TextView orderBy = (TextView) rowView.findViewById(R.id.orderBy);
                TextView deliveryTime = (TextView) rowView.findViewById(R.id.delivTime);
                TextView deliveryStatus = (TextView) rowView.findViewById(R.id.orderStatus);

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

                Picasso.with(context).load(delivery.logoUrl).resize(400, 300).into(restIcon);
                restName.setText(delivery.restaurantName);

                if (delivery.isOrderPlaced) {
                    deliveryStatus.setText(R.string.order_placed);
                } else if (delivery.soldOut) {
                    deliveryStatus.setText(R.string.sold_out);
                } else if (delivery.isPastCutoff) {
                    deliveryStatus.setText(R.string.is_past_cutoff);
                } else if (delivery.sellingOut) {
                    deliveryStatus.setText(R.string.selling_out);
                } else {
                    deliveryStatus.setText(R.string.order_now);
                }
                break;
            }
        }
        return rowView;
    }

}
