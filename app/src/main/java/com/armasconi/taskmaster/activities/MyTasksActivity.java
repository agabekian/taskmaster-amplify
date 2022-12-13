package com.armasconi.taskmaster.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.MyTask;
import com.armasconi.taskmaster.R;
import com.armasconi.taskmaster.adapter.TaskRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyTasksActivity extends AppCompatActivity {
    public final static String TAG = "MyTaskActivity";
    public static final String MY_TASK_NAME = "taskTitle";
    public static final String MY_TASK_BODY = "taskDescription";
    public static final Boolean MY_TASK_STATE = false;
    TaskRecyclerViewAdapter adapter;
    private List<MyTask> allTasks;

//    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//    String teamname = preferences.getString(Settings.TEAMNAME_TAG, "No username");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); //back button
        actionBar.setHomeButtonEnabled(true);

        setupRecyclerView();
}
    @Override
    protected void onResume() {
        super.onResume();

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

    public void setupRecyclerView() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String teamname = preferences.getString(Settings.TEAMNAME_TAG, "No username");
        allTasks = new ArrayList<>();
        Amplify.API.query(
                ModelQuery.list(MyTask.class),
                success -> {
                    Log.i(TAG, "Read tasks successfully");
                    for (MyTask databaseTask : success.getData()) {
                        if(databaseTask.getTeam().getName().equals(teamname)){
                            allTasks.add(databaseTask);
                        }

                    }
                    runOnUiThread(() -> adapter.notifyDataSetChanged()); // since this runs asynchronously, the adapter may already have rendered, so we have to tell it to update
                },
                failure -> Log.e(TAG, "Failed to read TASKS from database")
        );
//        allTasks.add(new MyTask("Learn French", "Merci Pardon",MyTask.TaskStateEnum.NEW, new Date() ));
//        allTasks.add(new MyTask("Get in shape", "work work work", MyTask.TaskStateEnum.NEW, new Date()));
//        allTasks.add(new MyTask("Get a billion", "Dollars or roubles?", MyTask.TaskStateEnum.COMPLETED, new Date()));

        RecyclerView taskRV = findViewById(R.id.SuperPetRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        taskRV.setLayoutManager(layoutManager);
        adapter = new TaskRecyclerViewAdapter(allTasks, this);
        taskRV.setAdapter(adapter);
    }
}