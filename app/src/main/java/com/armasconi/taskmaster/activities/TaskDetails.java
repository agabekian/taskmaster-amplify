package com.armasconi.taskmaster.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;

import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.armasconi.taskmaster.R;


import java.io.File;
import java.util.Objects;

public class TaskDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        //BACK BUTTON - DRY?
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true); //back button
        actionBar.setHomeButtonEnabled(true);
        displayTaskName();
        displayTaskBody();
        displayImage();
    }


    @Override
    //actual back button functionality as per AI
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void displayTaskName() {
        Intent callingIntent = getIntent();
        String textStarter = null;
        if (callingIntent != null) {
            textStarter = callingIntent.getStringExtra(MyTasksActivity.MY_TASK_NAME);
        }
        TextView taskTextView = findViewById(R.id.taskTextView);
        taskTextView.setText(Objects.requireNonNullElse(textStarter, "Nada"));
    }

    public void displayTaskBody() {
        Intent callingIntent = getIntent();
        String textBody = null;

        if (callingIntent != null) {
            textBody = callingIntent.getStringExtra(MyTasksActivity.MY_TASK_BODY);
        }
        TextView textBodyView = findViewById(R.id.taskTextView2);
        textBodyView.setText(Objects.requireNonNullElse(textBody, "No body"));
    }

    public void displayImage() {
        Intent callingIntent = getIntent();
        String s3ImageKey = callingIntent.getStringExtra("s3ImageKey");
        if (s3ImageKey != null) {
            String[] segments = s3ImageKey.split("/"); //key has "public" prefix for odd reason so gotta do this
            s3ImageKey = segments[segments.length - 1];
            Amplify.Storage.downloadFile(
                    s3ImageKey,
                    new File(getApplication().getFilesDir(), s3ImageKey),
                    success -> {
                        ImageView displayImage = findViewById(R.id.displayImage);
                        displayImage.setImageBitmap(BitmapFactory.decodeFile(success.getFile().getPath()));
                    },
                    failure -> Log.i("TaskDetail", "failed image acquisition")
            );
        }
    }


//    public void displayTaskStatus() { //finish passing boolean
//        Intent callingIntent = getIntent();
//        Boolean status = false;
//        if (callingIntent != null) {
//            status = callingIntent.getBooleanExtra("statuS", MyTasksActivity.MY_TASK_STATE);
//        }
//        TextView statusView = findViewById(R.id.taskStatusView);
//        if (status != null) {
//            statusView.setText((status);
//        }
//
//    }
}