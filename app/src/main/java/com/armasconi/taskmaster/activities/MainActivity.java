package com.armasconi.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import android.widget.TextView;

import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.armasconi.taskmaster.R;
import com.armasconi.taskmaster.activities.auth.SignIn_Activity;
//import com.armasconi.taskmaster.activities.auth.SignUp_Activity;
//import com.armasconi.taskmaster.activities.auth.VerifySignUp_Activity;
import com.armasconi.taskmaster.activities.util.SignOutAmplify;

public class MainActivity extends AppCompatActivity {
    public final String TAG = "main_activity";
    public final static String SIGNUP_EMAIL_TAG = "email";
    public AuthUser authUser = null;
    Menu myMenu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //menu display
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        myMenu = menu; //target this "inflated" menu not menu in layout

        MenuItem signUpItem = menu.findItem(R.id.menuSignUpItem);
        MenuItem signInItem = menu.findItem(R.id.menuSignInItem);
        MenuItem signOutItem = menu.findItem(R.id.menuSignOutItem);
        if (authUser == null) {
            signUpItem.setVisible(true);
            signInItem.setVisible(true);
            signOutItem.setVisible(false);
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
    }
}