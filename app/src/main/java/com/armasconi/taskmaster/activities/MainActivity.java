package com.armasconi.taskmaster.activities;

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
import com.amplifyframework.auth.cognito.result.AWSCognitoAuthSignOutResult;
import com.amplifyframework.auth.cognito.result.GlobalSignOutError;
import com.amplifyframework.auth.cognito.result.HostedUIError;
import com.amplifyframework.auth.cognito.result.RevokeTokenError;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.armasconi.taskmaster.R;
import com.armasconi.taskmaster.activities.auth.SignIn_Activity;
import com.armasconi.taskmaster.activities.auth.SignUp_Activity;
import com.armasconi.taskmaster.activities.auth.VerifySignUp_Activity;

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
    }


    // TODO Step 3-1 Manual upload of a file to S3
//    String testFilename = "testFileName";
//    File testFile = new File(getApplicationContext().getFilesDir(), testFilename);
//
//    try
//    {
//      BufferedWriter testFileBufferedWriter = new BufferedWriter(new FileWriter(testFile));
//      testFileBufferedWriter.append("Some test text here\nAnother line of test text");
//      testFileBufferedWriter.close();  // Make sure to do this or the text may not be saved!
//    } catch (IOException ioe)
//    {
//      Log.e(TAG, "Could not write file locally with filename: " + testFilename);
//    }
//
//    String testFileS3Key = "someFileOnS3.txt";
//
//    Amplify.Storage.uploadFile(
//      testFileS3Key,
//      testFile,
//      success ->
//      {
//        Log.i(TAG, "S3 upload succeeded! Key is: " + success.getKey());
//      },
//      failure ->
//      {
//        Log.i(TAG, "S3 upload failed! " + failure.getMessage());
//      }
//    );
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //menu func


//        }
        switch (item.getItemId()) {

            case R.id.menuSignInItem:
                Intent j = new Intent(this, SignIn_Activity.class);
                this.startActivity(j);
                return true;

            case R.id.menuSignOutItem:
                Amplify.Auth.signOut(signOutResult -> {
                    if (signOutResult instanceof AWSCognitoAuthSignOutResult.CompleteSignOut) {
                        // Sign Out completed fully and without errors.
                        Log.i("AuthQuickStart", "Signed out successfully");
                    } else if (signOutResult instanceof AWSCognitoAuthSignOutResult.PartialSignOut) {
                        // Sign Out completed with some errors. User is signed out of the device.
                        AWSCognitoAuthSignOutResult.PartialSignOut partialSignOutResult =
                                (AWSCognitoAuthSignOutResult.PartialSignOut) signOutResult;

                        HostedUIError hostedUIError = partialSignOutResult.getHostedUIError();
                        if (hostedUIError != null) {
                            Log.e("AuthQuickStart", "HostedUI Error", hostedUIError.getException());
                            // Optional: Re-launch hostedUIError.getUrl() in a Custom tab to clear Cognito web session.
                        }

                        GlobalSignOutError globalSignOutError = partialSignOutResult.getGlobalSignOutError();
                        if (globalSignOutError != null) {
                            Log.e("AuthQuickStart", "GlobalSignOut Error", globalSignOutError.getException());
                            // Optional: Use escape hatch to retry revocation of globalSignOutError.getAccessToken().
                        }

                        RevokeTokenError revokeTokenError = partialSignOutResult.getRevokeTokenError();
                        if (revokeTokenError != null) {
                            Log.e("AuthQuickStart", "RevokeToken Error", revokeTokenError.getException());
                            // Optional: Use escape hatch to retry revocation of revokeTokenError.getRefreshToken().
                        }
                    } else if (signOutResult instanceof AWSCognitoAuthSignOutResult.FailedSignOut) {
                        AWSCognitoAuthSignOutResult.FailedSignOut failedSignOutResult =
                                (AWSCognitoAuthSignOutResult.FailedSignOut) signOutResult;
                        // Sign Out failed with an exception, leaving the user signed in.
                        Log.e("AuthQuickStart", "Sign out Failed", failedSignOutResult.getException());
                    }
                });
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
//        Button signUp = findViewById(R.id.MainActivitySignUpBttn);
//        Button signIn = findViewById(R.id.MainActivitySignInBttn);

        // TODO This is how you get the currentAuthUser
        Amplify.Auth.getCurrentUser(
                success -> {
                    Log.i(TAG, "User is authorized OK");
                    authUser = success;
                },
                failure -> {
                    Log.w(TAG, "No authenticated User present");
                }
        );

        //TODO 2-1 Hardcode signup, verify and login


        // Verfication
//      Amplify.Auth.confirmSignUp(
//        "alex.white@codefellows.com",
//        "578232",
//        success -> Log.i(TAG, "verify suceeded"),
//        failure -> Log.i(TAG, "verification failed: " + failure)
//      );
//
//    //Login
//
//    Amplify.Auth.signIn(
//      "alex.white@codefellows.com",
//      "p@ssw0rd",
//      success -> Log.i(TAG, "SignIn success!"),
//      failure -> Log.e(TAG, "SignIn failed")
//    );

        // Signout -> logout
//        Amplify.Auth.signOut(
//                () -> {
//                    success -> Log.i(TAG, "Logout succeeded!"),
//                            failure -> Log.i(TAG, "Logout failed: " + failure)
//                }
//        );
        setupBtns();
        setupGreeting();
    }

    public void setupGreeting() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String username = preferences.getString(UserProfileActivity.USERNAME_TAG, "No username");
        String teamname = preferences.getString(UserProfileActivity.TEAMNAME_TAG, "No username");
        ((TextView) findViewById(R.id.Main_TVPersonName)).setText(username + " (" + teamname + ")" + " Tasks"); //Main title
    }

    public void setupUserProfileImageButton() {
        ImageView userProfileLink = MainActivity.this.findViewById(R.id.MainActivityImageViewUserProfile);
        userProfileLink.setOnClickListener(v -> {
            Intent goToUserProfile = new Intent(this, UserProfileActivity.class);
            startActivity(goToUserProfile);
        });
    }

    public void setupBtns() {
//        Button signUp = findViewById(R.id.MainActivitySignUpBttn);
//        Button signIn = findViewById(R.id.MainActivitySignInBttn);
//        if (authUser == null) {
//            // not signed in: see sign up sign in hide logout
//            signIn.setVisibility(View.VISIBLE);
//            signUp.setVisibility(View.VISIBLE);
//        } else {
//            String username = authUser.getUsername();
//            Log.i(TAG, "Username is: " + username);
//            // signed in. hide sign up and sign in and show logout
//            signIn.setVisibility(View.INVISIBLE);
//            signUp.setVisibility(View.INVISIBLE);
//        }
        Button goToAllTasksBtn = MainActivity.this.findViewById(R.id.btnMainActivity_Tasks);
        goToAllTasksBtn.setOnClickListener(view -> {
            Button btnAllTasks = findViewById(R.id.btnMainActivity_Tasks);
            Intent goToTasks = new Intent(this, MyTasksActivity.class);
            startActivity(goToTasks);
        });

//        Button submitBtn = findViewById(R.id.btnAdd);     //1. get an UI element by id
//        // Sign in button
//
//        signIn.setOnClickListener(view -> {
//            Intent goToSignIn = new Intent(this, SignIn_Activity.class);
//            startActivity(goToSignIn);
//        });

//        Button signOutBtn = findViewById(R.id.switch1);
//        signOutBtn.setOnClickListener(v -> {
//                    Amplify.Auth.signOut(
//                            () ->
//                            {
//                                Log.i(TAG, "Logout succeeded!");
//                            },
//                            failure ->
//                            {
//                                Log.i(TAG, "Logout failed: " + failure);
//                            }
//                    );
//                }
//        );
//        Button verify = findViewById(R.id.MainActivityVerifyBttn);
//        verify.setOnClickListener(view -> {
//                    Intent goVerify = new Intent(MainActivity.this, VerifySignUp_Activity.class);
//                    goVerify.putExtra(SIGNUP_EMAIL_TAG, "2008nv@gmail.com");
//                    startActivity(goVerify);
//                }
//        );
        // sign up button
//        signUp.setOnClickListener(v -> {
//            Intent goToSignUp = new Intent(this, SignUp_Activity.class);
//            startActivity(goToSignUp);
//        });

//        ImageView UserProfile = findViewById(R.id.MainActivityImageViewUserProfile);
//        UserProfile.setOnClickListener(v -> {
//                    Intent goToUserProfile = new Intent(this, UserProfileActivity.class);
//                    startActivity(goToUserProfile);
//                }
//        );
//        signUp.setOnClickListener(v -> {
//            Amplify.Auth.signUp("2008nv@gmail.com",
//                    "p@ssw0rd",  // Cognito's default password policy is 8 characters, no other requirements
//                    AuthSignUpOptions.builder()
//                            .userAttribute(AuthUserAttributeKey.email(), "2008nv@gmail.com")
//                            .build(),
//                    success -> {
//                        Log.i(TAG, "Signup suceeded" + success);
//                    },
//                    failure -> {
//                        Log.w(TAG, "Signup dailed with email: " + "2008nv@gmail.com" + "with the message: " + failure);
//                    }
//            );
//            Toast.makeText(this,"works",Toast.LENGTH_SHORT).show();
//        });
        // get current authenticted user
        // if user is null -> show signUp button, hide signIn button

//        submitBtn.setOnClickListener(view -> {
//            Intent goToForm = new Intent(MainActivity.this, AddTask.class);
//            startActivity(goToForm);
        //TODO: send extra
        // set up the intent (Current context.this, class to go to Class.class)
//        Intent goDisplayTask1 = new Intent(this, TaskDetails.class);
//        btnDisplayTask1.setOnClickListener(view -> {
//            goDisplayTask1.putExtra(TASK_NAME, btnDisplayTask1.getText().toString());
        // launch the intent
//            startActivity(goDisplayTask1);
//
//        });
//        Intent goDisplayTask2 = new Intent(this, TaskDetails.class);
//        btnDisplayTask2.setOnClickListener(view -> {
//            goDisplayTask2.putExtra(TASK_NAME, btnDisplayTask2.getText().toString());
//            startActivity(goDisplayTask2);
//        });
//
//        Intent goDisplayTask3 = new Intent(this, TaskDetails.class);
//        btnDisplayTask3.setOnClickListener(view -> {
//            goDisplayTask3.putExtra(TASK_NAME, btnDisplayTask3.getText().toString());
//            startActivity(goDisplayTask3);
//        });
//        // setting up routing logic with intents. Intents are the highway between activities

//        });
    }
}