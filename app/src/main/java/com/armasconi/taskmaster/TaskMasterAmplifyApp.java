package com.armasconi.taskmaster;

import android.app.Application;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.analytics.pinpoint.AWSPinpointAnalyticsPlugin;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.amplifyframework.predictions.aws.AWSPredictionsPlugin;

public class TaskMasterAmplifyApp extends Application {
    public final static String TAG = "time_master_amplify_app";

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSPredictionsPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());

            Amplify.configure(getApplicationContext());
//            Amplify.addPlugin(new AWSPinpointAnalyticsPlugin(this));

        } catch (AmplifyException ae) {
            Log.e(TAG, "Error(!) iniitializing Amplify " + ae.getMessage(), ae);
        }
    }
}
