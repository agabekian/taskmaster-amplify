package com.armasconi.taskmaster.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.MyTask;
import com.amplifyframework.datastore.generated.model.TaskStateEnum;
import com.armasconi.taskmaster.R;
//import com.armasconi.taskmaster.database.TaskMasterDatabase;


import java.util.Date;

// TODO Step: 5-3 create and activity to take in input and save to DB
public class AddTask extends AppCompatActivity {

    public final String TAG = "AddTaskActivity";
    Spinner taskStateSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); //back button
        actionBar.setHomeButtonEnabled(true);

        taskStateSpinner = findViewById(R.id.AddTaskSpinnerState);
        setupTypeSpinner();
        setupSaveBttn();
    }

    // TODO Step: 5-4 setup spinner for enum
    public void setupTypeSpinner() {

        taskStateSpinner.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                TaskStateEnum.values()
        ));
    }

    //TODO Step: 5-5 save TASK to database onClick with the DAO
    public void setupSaveBttn() {
        Button btnSave = findViewById(R.id.AddTaskSaveBttn);
        btnSave.setOnClickListener(v -> {
            //build pattern for Amplify
            MyTask newTask = MyTask.builder()
                    .title(((EditText) findViewById(R.id.AddATaskETName)).getText().toString())
                    .body(((EditText) findViewById(R.id.AddTaskBody)).getText().toString())
                    .state((TaskStateEnum) taskStateSpinner.getSelectedItem())
                    .datePosted(new Temporal.DateTime(new Date(), 0))
                    .build();

            Amplify.API.mutate(
                    ModelMutation.create(newTask),
                    success -> Log.i(TAG, "add task activity.onCreate created this GoodToGo"),
                    failure -> Log.w(TAG, "add task activity.onCreate  FAIL", failure)
            );
            Toast.makeText(this, "New task added!", Toast.LENGTH_SHORT).show();
        });
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
}
