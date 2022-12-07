package com.armasconi.taskmaster.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.armasconi.taskmaster.database.TaskMasterDatabase;
import com.armasconi.taskmaster.models.MyTask;
import com.armasconi.taskmaster.R;
import com.armasconi.taskmaster.adapter.TaskRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyTasksActivity extends AppCompatActivity {
    public static final String MY_TASK_NAME = "taskTitle";
    public static final String MY_TASK_BODY = "taskDescription";
    public static final Boolean MY_TASK_STATE = false;
    TaskMasterDatabase taskMasterDatabase;

    // TODO: Step 1-1: Add a RecyclerView in the WSYIWYG editor
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); //back button
        actionBar.setHomeButtonEnabled(true);

        taskMasterDatabase = Room.databaseBuilder(
                        getApplicationContext(),
                        TaskMasterDatabase.class,
                        MainActivity.DATABASE_NAME)
                .fallbackToDestructiveMigration() // If Room gets confused, it tosses your database; don't use this in production!
                .allowMainThreadQueries()
                .build();
        setupRecyclerView();
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
        // TODO Step 2-2:  Make some hardcoded data items
        // TODO Step 6-1 find all TASKS from database
        List<MyTask> tasks = taskMasterDatabase.tasksDao().findAll();
        //      tasks.add(new SuperPet("Zork", SuperPet.SuperPetTypeEnum.FIRE, 7, new Date()));
//      tasks.add(new SuperPet("Lemi", SuperPet.SuperPetTypeEnum.FIRE, 7, new Date()));
        // TODO Step 1-2:  Grab the RecyclerView
        RecyclerView taskRV = findViewById(R.id.SuperPetRecyclerView);
        // TODO Step 1-3: Set the layout manager of the RecyclerView to a LinearLayoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        taskRV.setLayoutManager(layoutManager);
        // TODO Step 1-5: Create and attach the RecyclerView.Adapter
        // TODO Step 2-3: (In this activity and RecyclerViewAdapter) Hand in some data items
        // TODO Step 3-2: (In this activity and RecyclerViewAdapter) Hand in the Activity context
        TaskRecyclerViewAdapter adapter = new TaskRecyclerViewAdapter(tasks, this);
        taskRV.setAdapter(adapter);
//    public void setupRecyclerView() {
//        // TODO Step 2-2:  Make some hardcoded data items
//        List<MyTask> allTasks = new ArrayList<>();
//        allTasks.add(new MyTask("Learn French", "Merci Pardon", true));
//        allTasks.add(new MyTask("Get in shape", "work work work", true));
//        allTasks.add(new MyTask("Get a billion", "Dollars or roubles?", false));
//        allTasks.add(new MyTask("Learn Italian", "Ciao", true));
//        allTasks.add(new MyTask("Get not too drunk on holidays", "trudging a road to a happy...", true));
//        allTasks.add(new MyTask("Get some", "LA or SD?", false));
//        allTasks.add(new MyTask("Ideas for therapist", "Everett", false));
//        allTasks.add(new MyTask("Get some", "LA or SD?", false));
//        allTasks.add(new MyTask("Learn French", "Merci Pardon", true));
//        allTasks.add(new MyTask("Get in shape", "work work work", true));
//        allTasks.add(new MyTask("Get a billion", "Dollars or roubles?", false));
//        allTasks.add(new MyTask("Learn Italian", "Ciao", true));
//
//        // TODO Step 1-2:  Grab the RecyclerView
//        RecyclerView superPetRV = findViewById(R.id.SuperPetRecyclerView);
//        // TODO Step 1-3: Set the layout manager of the RecyclerView to a LinearLayoutManager
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        superPetRV.setLayoutManager(layoutManager);
//        // TODO Step 1-5: Create and attach the RecyclerView.Adapter
//        // TODO Step 2-3: (In this activity and RecyclerViewAdapter) Hand in some data items
//        // TODO Step 3-2: (In this activity and RecyclerViewAdapter) Hand in the Activity context
//        TaskRecyclerViewAdapter adapter = new TaskRecyclerViewAdapter(allTasks, this);
//        superPetRV.setAdapter(adapter);
//    }
    }
}