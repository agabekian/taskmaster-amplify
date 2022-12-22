package com.armasconi.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.analytics.AnalyticsEvent;
//import com.amplifyframework.analytics.pinpoint.AWSPinpointAnalyticsPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.armasconi.taskmaster.R;

import android.os.Bundle;
import android.util.Log;

import java.util.Date;

public class Analytics_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);
        AnalyticsEvent event = AnalyticsEvent.builder()
                .name("Opened Analytics Activity")
                .addProperty("Time", Long.toString(new Date().getTime()))
                .addProperty("trackingEvent", "Analytics activity was opened")
                .build();

        Amplify.Analytics.recordEvent(event);
    }


}