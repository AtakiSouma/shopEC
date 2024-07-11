package com.example.online_shop_app.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {
    public static String formatTimestamp(String timestamp) {
        try {
            // Parse the ISO 8601 timestamp
            SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            isoFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // Set the timezone to UTC
            Date date = isoFormat.parse(timestamp);

            // Format the date to a readable format with a shorter day of the week and without seconds
            SimpleDateFormat readableFormat = new SimpleDateFormat("EEE, MMM d, yyyy, h:mm a", Locale.getDefault());
            return readableFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return timestamp; // Return the original timestamp in case of an error
        }
    }

}
