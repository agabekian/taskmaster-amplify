package com.armasconi.taskmaster;

import android.app.Application;
import android.util.Log;

import com.amplifyframework.AmplifyException;
//import com.amplifyframework.analytics.pinpoint.AWSPinpointAnalyticsPlugin;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;


public class TaskMasterAmplifyApp extends Application {
    public final static String TAG = "time_master_amplify_app";

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
//            Amplify.addPlugin(new AWSPinpointAnalyticsPlugin());

            Amplify.configure(getApplicationContext());
        } catch (AmplifyException ae) {
            Log.e(TAG, "Error on Amplify init" + ae.getMessage(), ae);
        }
    }
}