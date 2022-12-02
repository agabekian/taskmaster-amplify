package com.armasconi.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

        @SuppressLint("SetTextI18n")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            //1. get an UI element by id
            Button submitBtn = MainActivity.this.findViewById(R.id.btnAdd);

            //2. add an event listener
            submitBtn.setOnClickListener(view -> {
                Context context = getApplicationContext();
                CharSequence text = "Submitted!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                //3. Callback fn()
                //4. do stuff in the callback
                System.out.println("ZORK WAS HERE");

                Log.v("", "Very Verbose");
                Log.d("", "Debug");
                Log.i("", "Information");
                Log.w("", "Warning");
                Log.e("", "Error");
                Log.wtf("", "What a terrible failure");
                TextView greeting = MainActivity.this.findViewById(R.id.btnAdd);
//           ´

            });

            setupBttns();
        }

        public void setupBttns(){
            Button goToAllTasksBtn = MainActivity.this.findViewById(R.id.btnAll);
            // setting up routing logic with intents. Intents are the highway between activities
            goToAllTasksBtn.setOnClickListener(view -> {
                // set up the intent (Current context.this, class to go to Class.class)
                Intent goToAllTasks = new Intent(this, AllTasks.class);
                // launch the intent
                startActivity(goToAllTasks);
            });
        }
}