package com.armasconi.taskmaster.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.MyTask;
import com.armasconi.taskmaster.R;
import com.armasconi.taskmaster.adapter.TaskRecyclerViewAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyTasksActivity extends AppCompatActivity {
    public final static String TAG = "MyTaskActivity";
    public static final String MY_TASK_NAME = "taskTitle";
    public static final String MY_TASK_BODY = "taskDescription";

//    public static final Boolean MY_TASK_STATE = false;
    TaskRecyclerViewAdapter adapter;
    private List<MyTask> allTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); //back button
        actionBar.setHomeButtonEnabled(true);

        setupRecyclerView();
        displayImage();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    //back button functionality as per AI
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setupRecyclerView() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String teamname = preferences.getString(UserProfileActivity.TEAMNAME_TAG, "No username");
        allTasks = new ArrayList<>();
        Amplify.API.query(
                ModelQuery.list(MyTask.class),
                success -> {
                    Log.i(TAG, "Read tasks successfully");
                    for (MyTask databaseTask : success.getData()) {
                        if (databaseTask.getTeam().getName().equals(teamname)) { //display by team
                            allTasks.add(databaseTask);
                        }
                    }
                    runOnUiThread(() -> adapter.notifyDataSetChanged()); // since this runs asynchronously, the adapter may already have rendered, so we have to tell it to update
                },
                failure -> Log.e(TAG, "Failed to read TASKS from database")
        );
//        allTasks.add(new MyTask("Learn French", "Merci Pardon",MyTask.TaskStateEnum.NEW, new Date() ));
        RecyclerView taskRV = findViewById(R.id.SuperPetRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        taskRV.setLayoutManager(layoutManager);

        adapter = new TaskRecyclerViewAdapter(allTasks, this);
        taskRV.setAdapter(adapter);
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

}