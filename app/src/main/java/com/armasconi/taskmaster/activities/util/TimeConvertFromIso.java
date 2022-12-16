package com.armasconi.taskmaster.activities.util;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;

import com.amplifyframework.core.model.temporal.Temporal;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class TimeConvertFromIso {
    public static String convert(Temporal.DateTime time) {
        DateFormat dateCreatedIso8601InputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                Locale.getDefault());
        dateCreatedIso8601InputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        android.icu.text.DateFormat dateCreatedOutputFormat = new SimpleDateFormat("MM-dd-yy HH:mm"); //:ss
        dateCreatedOutputFormat.setTimeZone(TimeZone.getDefault());
        String dateCreatedString = "";
        try {
            Date dateCreatedJavaDate = dateCreatedIso8601InputFormat.parse(time.format());
            if (dateCreatedJavaDate != null)
                dateCreatedString = dateCreatedOutputFormat.format(dateCreatedJavaDate);
            return dateCreatedString;
        } catch (
                ParseException e) {
            return "Error parsing date";
        }
    }
}