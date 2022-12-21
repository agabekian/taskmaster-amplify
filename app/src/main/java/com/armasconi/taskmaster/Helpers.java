package com.armasconi.taskmaster;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;


public class Helpers extends AppCompatActivity  {

    @Override
    //back button functionality as per AI
     public  boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
