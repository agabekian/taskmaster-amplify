package com.armasconi.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.armasconi.taskmaster.R;
import com.armasconi.taskmaster.database.TaskMasterDatabase;
import com.armasconi.taskmaster.models.MyTask;

import java.util.Date;

// TODO Step: 5-3 create and activity to take in input and save to DB
public class AddTask extends AppCompatActivity {

        TaskMasterDatabase taskMasterDatabase;
        Spinner taskStateSpinner;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_task);
            taskStateSpinner = findViewById(R.id.AddTaskSpinnerState);
            //TODO Step: 5-6 instantiate the DB wherever you need it
            taskMasterDatabase = Room.databaseBuilder(
                            getApplicationContext(),
                            TaskMasterDatabase.class,
                            MainActivity.DATABASE_NAME)
                    .fallbackToDestructiveMigration() // If Room gets confused, it tosses your database; don't use this in production!
                    .allowMainThreadQueries()
                    .build();
            setupTypeSpinner();
            setupSaveBttn();
        }

        // TODO Step: 5-4 setup spinner for enum
        public void setupTypeSpinner(){

            taskStateSpinner.setAdapter(new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_item,
                    MyTask.TaskStateEnum.values()
            ));
        }

        //TODO Step: 5-5 save TASK to database onClick with the DAO
        public void setupSaveBttn(){
            Button btnSave = findViewById(R.id.AddTaskSaveBttn);
            btnSave.setOnClickListener(v -> {
                MyTask newTask = new MyTask(
                        ((EditText)findViewById(R.id.AddATaskETName)).getText().toString(),
                        ((EditText)findViewById(R.id.AddTaskBody)).getText().toString(),
                        MyTask.TaskStateEnum.fromString(taskStateSpinner.getSelectedItem().toString()),
                        new Date()
                );
                long id = taskMasterDatabase.tasksDao().insertTask(newTask);
                Toast.makeText(this, "New task added!", Toast.LENGTH_SHORT).show();
            });
        }
    }
//