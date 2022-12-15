package com.armasconi.taskmaster.activities;

import android.net.Uri;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;
//import androidx.room.Room;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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


import java.io.FileNotFoundException;
import java.io.InputStream;
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
    CompletableFuture<List<Team>> teamsFuture = new CompletableFuture<>();
    // TODO Step 3-3 setup the activityResultLauncher
    ActivityResultLauncher<Intent> activityResultLauncher; // at top of class
    private String s3ImageKey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // TODO Step 3-3 setup the activityResultLauncher


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

        activityResultLauncher = getImagePickingActivityResultLauncher();
        teamSpinner = findViewById(R.id.AddATeamSpinner);
        taskStateSpinner = findViewById(R.id.AddTaskSpinnerState);

        // TODO query and setup a spinner for teams
        // Compeleteable Future
        Amplify.API.query(
                ModelQuery.list(Team.class),
                success -> {
                    Log.i(TAG, "Read Team Successfully");
                    ArrayList<String> teamNames = new ArrayList<>();
                    ArrayList<Team> teams = new ArrayList<>();
                    for (Team team : success.getData()) {
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
        setupTypeSpinner();
        setupSaveBttn();
        setupAddImageBttn();
    }

    public void setupTeamSpinner(ArrayList<String> teamNames) {
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

    // TODO 3-2 setupAddImageBttn
    private void setupAddImageBttn() {
        findViewById(R.id.AddASuperPetBttnAddImage).setOnClickListener(v -> {
            launchImageSelectionIntent();
        });
    }

    // Todo Step 3-4 create launchImageSelectionIntent
    private void launchImageSelectionIntent() {
        Intent imageFilePickingIntent = new Intent(Intent.ACTION_GET_CONTENT); // one of several file picking activities built into Android
        //TODO play around with setTypes
        imageFilePickingIntent.setType("*/*");  // only allow one kind or category of file; if you don't have this, you get a very cryptic error about "No activity found to handle Intent
        imageFilePickingIntent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/jpeg", "image/png"}); // only pick JPEG and PNG images
        // Launch Android's built-in file picking activity using our newly created ActivityResultLauncher below
        activityResultLauncher.launch(imageFilePickingIntent);
    }

    // TODO Step 3-5 getImagePickingActivityResultLauncher
    private ActivityResultLauncher<Intent> getImagePickingActivityResultLauncher() {
        ActivityResultLauncher<Intent> imagePickingActivityResultLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        result -> {
                            Uri pickedImageFileUri = result.getData().getData();
                            try {
                                // take in the file URI and turn it into a inputStream
                                InputStream pickedImageInputStream = getContentResolver().openInputStream(pickedImageFileUri);
                                String pickedImageFilename = getFileNameFromUri(pickedImageFileUri);
//                                String pickedImageFilename = DocumentFile.fromSingleUri(this, pickedImageFileUri).getName();
                                Log.i(TAG, "Succeeded in getting input stream from file on phone! Filename is: " + pickedImageFilename);
                                uploadInputStreamToS3(pickedImageInputStream, pickedImageFilename, pickedImageFileUri);
                            } catch (FileNotFoundException fnfe) {
                                Log.e(TAG, "Could not get file from file picker" + fnfe.getMessage());
                            }
                        }
                );
        return imagePickingActivityResultLauncher;
    }

    // TODO Step 3-6 uploadInputStreamToS3
    private void uploadInputStreamToS3(InputStream pickedImageInputStream, String pickedImageFilename, Uri pickedImageFileUri) {
        Amplify.Storage.uploadInputStream(
                pickedImageFilename,
                pickedImageInputStream,
                success -> {
                    Log.i(TAG, "Succeeded in getting file uploaded to S3! Key is: " + success.getKey());
                    s3ImageKey = success.getKey();
                    // TODO:
//          saveSuperPet();
                    ImageView superPetImage = findViewById(R.id.ADdAASuperPetImageViewImage);
                    InputStream pickedImageInputStreamCopy = null; // need to make a copy because InputStreams cannot be reused!
                    try {
                        pickedImageInputStreamCopy = getContentResolver().openInputStream(pickedImageFileUri);
                    } catch (FileNotFoundException fnfe) {
                        Log.e(TAG, "Could not get file stream from URI! " + fnfe.getMessage(), fnfe);
                    }
                    superPetImage.setImageBitmap(BitmapFactory.decodeStream(pickedImageInputStreamCopy));
                },
                failure -> Log.e(TAG, "Failure in uploading file to S3 with filename: " + pickedImageFilename + " with error: " + failure.getMessage())
        );
    }

    // OUTDATED!!!!
    //TODO read through this helper function
    // TODO consider other easy to get filename from URI -> STRETCH GOAL
    // Taken from https://stackoverflow.com/a/25005243/16889809

    @SuppressLint("Range")
    public String getFileNameFromUri(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }


    // TODO possible refactor
//    public String getFileNameFromURI(URI uri) throws MalformedURLException {
//      URL url = uri.toURL();
//      String filePath = url.getFile();
//      String[] segments = filePath.split("/");
//      return segments[segments.length - 1];
//    }
//    }

    //TODO Step: 5-5 save TASK to database onClick with the DAO
    public void setupSaveBttn() {
        Button btnSave = findViewById(R.id.AddTaskSaveBttn);
        btnSave.setOnClickListener(v -> {
            String selectedTeamString = teamSpinner.getSelectedItem().toString();
            List<Team> teams = null;
            try {
                // Todo Step: 1-4 implement CompleteableFuture
                teams = teamsFuture.get();
            } catch (InterruptedException ie) {
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
                    .s3ImageKey(s3ImageKey)
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
