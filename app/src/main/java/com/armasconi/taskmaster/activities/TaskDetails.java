package com.armasconi.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.armasconi.taskmaster.R;

public class TaskDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        displayTaskName();
    }

    public void displayTaskName(){
        Intent callingIntent = getIntent();
        String textStarter = null;


        if(callingIntent != null){
            textStarter = callingIntent.getStringExtra(MainActivity.TASK_NAME);
        }
        TextView taskTextView = findViewById(R.id.taskTextView);
        if(textStarter != null) {
            taskTextView.setText(textStarter);
        }
        else {
            taskTextView.setText("Nada");
        }
    }
}