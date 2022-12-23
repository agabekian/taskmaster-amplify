package com.armasconi.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import com.amplifyframework.analytics.AnalyticsEvent;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;
import com.armasconi.taskmaster.R;

import com.armasconi.taskmaster.activities.auth.SignIn_Activity;
import com.armasconi.taskmaster.activities.auth.SignUp_Activity;
import com.armasconi.taskmaster.activities.auth.VerifySignUp_Activity;
import com.armasconi.taskmaster.activities.util.SignOutAmplify;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public final String TAG = "main_activity";
    public final static String SIGNUP_EMAIL_TAG = "email";
    public AuthUser authUser = null;
    private final MediaPlayer mp = new MediaPlayer();
    Menu myMenu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //menu display
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        myMenu = menu; //target this "inflated" menu not menu in layout

        MenuItem signUpItem = menu.findItem(R.id.menuSignUpItem);
        MenuItem signInItem = menu.findItem(R.id.menuSignInItem);
        MenuItem signOutItem = menu.findItem(R.id.menuSignOutItem);
        MenuItem verifyItem = menu.findItem(R.id.menuVerify);
        if (authUser == null) {
            signUpItem.setVisible(true);
            signInItem.setVisible(true);
            signOutItem.setVisible(false);
            verifyItem.setVisible(true);
        } else {
            signUpItem.setVisible(false);
            signInItem.setVisible(false);
            signOutItem.setVisible(true);
        }

        return true;
    } //Menu and visibilty logic

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //menu func
        switch (item.getItemId()) {
            case R.id.menuSignInItem:
                Intent j = new Intent(this, SignIn_Activity.class);
                this.startActivity(j);
                return true;

            case R.id.menuSignOutItem:
                SignOutAmplify.SignOut();
                return true;

            case R.id.menuVerify:
                Intent v = new Intent(this, VerifySignUp_Activity.class);
                this.startActivity(v);
                return true;
            case R.id.menuSignUpItem:
                Intent k = new Intent(this, SignUp_Activity.class);
                this.startActivity(k);
                return true;

            case R.id.MainActivityImageViewUserProfile:
                Intent l = new Intent(this, UserProfileActivity.class);
                this.startActivity(l);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Amplify.Predictions.convertTextToSpeech(
//                "I like to cha cha!",
//                result -> playAudio(result.getAudioData()),
//                error -> Log.e("MyAmplifyApp", "Conversion failed", error)
//        );

        AnalyticsEvent event = AnalyticsEvent.builder()
                .name("Opened App")
                .addProperty("Time", Long.toString(new Date().getTime()))
                .addProperty("trackingEvent", "Main activity was opened")
                .build();

        Amplify.Analytics.recordEvent(event);

        // TODO Get the currentAuthUser
        Amplify.Auth.getCurrentUser(
                success -> {
                    Log.i(TAG, "User is authorized OK");
                    authUser = success;
                },
                failure -> Log.w(TAG, "No authenticated User present")
        );
        setupButtons();
        setupGreeting();
    }
    private void playAudio(InputStream data) {
        File mp3File = new File(getCacheDir(), "audio.mp3");

        try (OutputStream out = new FileOutputStream(mp3File)) {
            Log.i(TAG, "Reading input stream");
            byte[] buffer = new byte[8 * 1_024];
            int bytesRead;
            while ((bytesRead = data.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            mp.reset();
            mp.setOnPreparedListener(MediaPlayer::start);
            mp.setDataSource(new FileInputStream(mp3File).getFD());
            mp.prepareAsync();
        } catch (IOException error) {
            Log.e("MyAmplifyApp", "Error writing audio file", error);
        }

    }
    @Override
    protected void onPause() {
        super.onPause();
        AnalyticsEvent event = AnalyticsEvent.builder()
                .name("Paused main activity")
                .addProperty("Time", Long.toString(new Date().getTime()))
                .addProperty("trackingEvent", "Main activity was paused")
                .build();
        Amplify.Analytics.recordEvent(event);
    }


    @SuppressLint("SetTextI18n")
    public void setupGreeting() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String username = preferences.getString(UserProfileActivity.USERNAME_TAG, "No username");
        String teamname = preferences.getString(UserProfileActivity.TEAMNAME_TAG, "No username");
        ((TextView) findViewById(R.id.Main_TVPersonName)).setText(username + " (" + teamname + ")" + " Tasks"); //Main title
    }

    public void setupButtons() {
        Button goToAllTasksBtn = MainActivity.this.findViewById(R.id.btnMainActivity_Tasks);
        goToAllTasksBtn.setOnClickListener(view -> {
            Intent goToTasks = new Intent(this, MyTasksActivity.class);
            startActivity(goToTasks);
        });

        Button submitBtn = findViewById(R.id.btnAdd);
        submitBtn.setOnClickListener(view -> {
            Intent goToForm = new Intent(MainActivity.this, AddTask.class);
            startActivity(goToForm);
        });

        Button goToLocationBtn = MainActivity.this.findViewById(R.id.locateBtn);
        goToLocationBtn.setOnClickListener(view -> {
            Intent goToLocate = new Intent(this, LocationActivity.class);
            Toast.makeText(this, "Getting location!", Toast.LENGTH_SHORT).show();
            startActivity(goToLocate);
        });
        Button goToAdsActivity = findViewById(R.id.MainActivityAdsBttn);
        goToAdsActivity.setOnClickListener(v -> {
            Intent goToAdsActivityIntent = new Intent(this, AdsActivity.class);
            startActivity(goToAdsActivityIntent);
        });

    }
}