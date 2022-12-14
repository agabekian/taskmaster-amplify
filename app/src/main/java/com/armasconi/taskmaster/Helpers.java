package com.armasconi.taskmaster;


import androidx.appcompat.app.AppCompatActivity;
//import androidx.room.Room;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.armasconi.taskmaster.R;
import com.armasconi.taskmaster.activities.auth.SignIn_Activity;
import com.armasconi.taskmaster.activities.auth.SignUp_Activity;
import com.armasconi.taskmaster.activities.auth.VerifySignUp_Activity;
import android.view.MenuItem;

public class Helpers extends AppCompatActivity  {

    @Override
    //back button functionality as per AI
     public  boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
