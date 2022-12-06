package com.armasconi.taskmaster.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.armasconi.taskmaster.R;

public class Settings extends AppCompatActivity {
    SharedPreferences preferences;
    public static final String USERNAME_TAG = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); //back button
        actionBar.setHomeButtonEnabled(true);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        saveValuesToSharedPrefs();


}

    @Override
    //actual back button functionality as per AI
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void saveValuesToSharedPrefs() {
        // Setup the editor -> sharedPrefs is read only by default
        SharedPreferences.Editor preferenceEditor = preferences.edit();
        Button saveButton = Settings.this.findViewById(R.id.UserProfileSaveBttn);
        saveButton.setOnClickListener(v -> {
            //method 1 to get input as a string
            String usernameText = ((EditText) findViewById(R.id.UserProfileETUsername)).getText().toString();
            //method 2 to get input as a string
//            EditText userPhoneNumber = findViewById(R.id.UserProfileETPhoneNumber);
//            String userPNString = userPhoneNumber.getText().toString();
            // method 1
            preferenceEditor.putString(USERNAME_TAG, usernameText);

            preferenceEditor.apply(); // TODO: Nothing saves unless you do this, DONT FORGET!!

            Toast.makeText(this, "Settings Saved!", Toast.LENGTH_SHORT).show();
        });
    }
    //1. collect the input
    // 2. set up on click for the save bttn
    //  a. save into shared prefs
}
