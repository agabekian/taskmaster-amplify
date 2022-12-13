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
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Team;
import com.amplifyframework.datastore.generated.model.MyTask;
import com.amplifyframework.datastore.generated.model.TaskStateEnum;
import com.armasconi.taskmaster.R;
//import com.armasconi.taskmaster.database.TaskMasterDatabase;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

// TODO Step: 5-3 create and activity to take in input and save to DB
public class AddTask extends AppCompatActivity {

    public final String TAG = "AddTaskActivity";
    Spinner taskStateSpinner;
    // Todo Step: 1-3 setup team spinner
    Spinner teamSpinner;
    // Todo Step: 1-4 implement CompleteableFuture
    CompletableFuture<List<Team>>teamsFuture = new CompletableFuture<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); //back button
        actionBar.setHomeButtonEnabled(true);
        // Todo Step: 1-2 hardcode/add 3 teams to AWS db
//      Team newTeam1 = Team.builder()
//        .name("Team_A")
//        .build();
//      Team newTeam2 = Team.builder()
//        .name("Team_B")
//
//        .build();
//      Team newTeam3 = Team.builder()
//        .name("Team_C")
//
//        .build();
//      Amplify.API.mutate(
//        ModelMutation.create(newTeam1),
//        success -> {},
//        failure -> {}
//      );
//      Amplify.API.mutate(
//        ModelMutation.create(newTeam2),
//        success -> {},
//        failure -> {}
//      );
//      Amplify.API.mutate(
//        ModelMutation.create(newTeam3),
//        success -> {},
//        failure -> {}
//      );

        // Todo Step: 1-3 setup team spinner
        teamSpinner = findViewById(R.id.AddATeamSpinner);

        // TODO query and setup a spinner for teams
        // Compeleteable Future
        Amplify.API.query(
                ModelQuery.list(Team.class),
                success -> {
                    Log.i(TAG, "Read Team Successfully");
                    ArrayList<String> teamNames = new ArrayList<>();
                    ArrayList<Team> teams = new ArrayList<>();
                    for (Team team: success.getData()) {
                        teamNames.add(team.getName());
                        teams.add(team);
                    }
                    // Todo Step: 1-4 implement CompleteableFuture
                    teamsFuture.complete(teams);
                    runOnUiThread(() -> {
                        // Todo Step: 1-3 setup team spinner
                        setupTeamSpinner(teamNames);
                    });
                },
                failure -> {
                    // Todo Step: 1-4 implement CompleteableFuture
                    teamsFuture.complete(null);
                    Log.w(TAG, "Failed to read Teams from Database");
                }
        );
        taskStateSpinner = findViewById(R.id.AddTaskSpinnerState);
        setupTypeSpinner();
        setupSaveBttn();
    }
    public void setupTeamSpinner(ArrayList<String> teamNames){
        teamSpinner.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                teamNames
        ));
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
            String selectedTeamString = teamSpinner.getSelectedItem().toString();
            List<Team> teams = null;
            try{
                // Todo Step: 1-4 implement CompleteableFuture
                teams = teamsFuture.get();
            }
            catch (InterruptedException ie) {
                Log.e(TAG, "InterruptedException while getting teams");
                Thread.currentThread().interrupt();
            } catch (ExecutionException ee) {
                Log.e(TAG, "ExecutionException while getting  teams");
            }
            Team selectedTeam = teams.stream().filter(t -> t.getName().equals(selectedTeamString)).findAny().orElseThrow(RuntimeException::new);

            //build pattern for Amplify
            MyTask newTask = MyTask.builder()
                    .title(((EditText) findViewById(R.id.AddATaskETName)).getText().toString())
                    .body(((EditText) findViewById(R.id.AddTaskBody)).getText().toString())
                    .state((TaskStateEnum) taskStateSpinner.getSelectedItem())
                    .team(selectedTeam)
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
