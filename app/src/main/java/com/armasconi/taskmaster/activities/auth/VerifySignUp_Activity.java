package com.armasconi.taskmaster.activities.auth;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.core.Amplify;
import com.armasconi.taskmaster.R;
import com.armasconi.taskmaster.activities.MainActivity;

import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class VerifySignUp_Activity extends AppCompatActivity {
    public static final String TAG = "VerifyAccountActivity";
    Intent callingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_sign_up);
        callingIntent = getIntent();

        setUpVerifyForm();
    }

    public void setUpVerifyForm() {
        // accept extra from calling activity
        // gather info -> email & verification code
//        String userEmail = callingIntent.getStringExtra(SignUp_Activity.SIGNUP_EMAIL_TAG);
        String userEmail = callingIntent.getStringExtra(MainActivity.SIGNUP_EMAIL_TAG);
        ((TextView) findViewById(R.id.emailBox)).setText("for da usa " + userEmail);
//        ((TextView) findViewById(R.id.editTextTextPersonName)).setText(username + " (" + teamname + ")" + " Tasks"); //Main title
        findViewById(R.id.verifyBttnVerify).setOnClickListener(v -> {
            String verificationCode = ((EditText) findViewById(R.id.verifyETConfirmationCode)).getText().toString();
            //verify call to Cognito
            Amplify.Auth.confirmSignUp(
                    userEmail,
                    verificationCode,
                    success -> {
                        Log.i(TAG, "Verification succeeded: " + success.toString());
                        Intent goToSignInActivity = new Intent(this, SignIn_Activity.class);
                        goToSignInActivity.putExtra(SignUp_Activity.SIGNUP_EMAIL_TAG, userEmail);
                        startActivity(goToSignInActivity);
                    },
                    failure -> {
                        Log.i(TAG, "Verification failed with username: " + userEmail + " with this message: " + failure);
                        runOnUiThread(() -> Toast.makeText(VerifySignUp_Activity.this, "Verify account failed!", Toast.LENGTH_SHORT));
                    }
            );
        });
    }
}