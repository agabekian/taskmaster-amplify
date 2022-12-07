package com.armasconi.taskmaster.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.armasconi.taskmaster.R;
import com.armasconi.taskmaster.activities.MyTasksActivity;
import com.armasconi.taskmaster.activities.TaskDetails;
import com.armasconi.taskmaster.models.MyTask;

import java.util.List;

//TODO Step 1-4: Make a class whose sole purpose is to manage RecyclerViews: a RecyclerView.Adapter
// TODO Step 3-1: (In RecyclerViewAdapter) Clean up the RecyclerView.Adapter references to actually use TaskRecyclerViewAdapter
// TODO Step 2-3: (In this activity and RecyclerViewAdapter) Hand in some data items

public class TaskRecyclerViewAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<TaskRecyclerViewAdapter.MyTaskViewHolder> { //from bottom lone 66
    List<MyTask> allTasks;
    Context callingActivity;

    public TaskRecyclerViewAdapter(List<MyTask> allTasks, Context callingActivity) {
        this.allTasks = allTasks;
        this.callingActivity = callingActivity;
    }

    @NonNull
    @Override
    public MyTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // TODO Step 1-7: (In RecyclerViewAdapter.onCreateViewHolder()) Inflate fragment
        View myTaskTextViewName = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_my_task, parent, false); //dynamic part -FALSE part
        // TODO Step 1-9: (In RecyclerViewAdapter.onCreateViewHolder()) Attach Fragment to ViewHolder
        return new MyTaskViewHolder(myTaskTextViewName);
    }
    @Override
    public void onBindViewHolder(@NonNull MyTaskViewHolder holder, int position) {
        // TODO Step 2-4: (In RecyclerViewAdapter.onBindViewHolder()) Bind data items to Fragments inside of ViewHolders
        TextView taskFragmentTextViewName = holder.itemView.findViewById(R.id.MyTaskFragTVName);
        // TODO Step 6-2 refactor the rendering
        MyTask task = allTasks.get(position);
        taskFragmentTextViewName.setText((position+1) + ". " + task.getTitle()
                + "\n" + task.getState()
                + "\n" + task.getDatePosted());
        // TODO Step 3-3: (In RecyclerViewAdapter.onBindViewHolder()) Create OnClickListener, make an Intent inside it, and call this Intent with an Extra to go to another Activity
        View taskItemView = holder.itemView;
        taskItemView.setOnClickListener(v -> {
            Intent goToOrderFormIntent = new Intent(callingActivity, TaskDetails.class);
            goToOrderFormIntent.putExtra(MyTasksActivity.MY_TASK_NAME, task.getTitle());
            callingActivity.startActivity(goToOrderFormIntent);
        });
    }
//    @Override
//    public void onBindViewHolder(@NonNull MyTaskViewHolder holder, int position) {
//        // TODO Step 2-4: (In RecyclerViewAdapter.onBindViewHolder()) Bind data items to Fragments inside of ViewHolders
//        TextView taskRecyclerTextViewHolder = holder.itemView.findViewById(R.id.MyTaskFragTVName);
//        TextView taskRecyclerTypeViewHolder = holder.itemView.findViewById(R.id.MyTaskFragTVType);
//
//        String myTaskName = allTasks.get(position).getTitle();
//        String myTaskBody = allTasks.get(position).getBody();
//        TaskStateEnum myTaskState = allTasks.get(position).getState();
//        taskRecyclerTextViewHolder.setText((position + 1) + ". " + myTaskName); //index
//        taskRecyclerTypeViewHolder.setText(myTaskBody);
//        // TODO Step 3-3: (In RecyclerViewAdapter.onBindViewHolder()) Create OnClickListener, make an Intent inside it, and call this Intent with an Extra to go to another Activity
//        View myTaskViewHolder = holder.itemView;
//        myTaskViewHolder.setOnClickListener(v -> {
//            Intent goToAllTasks = new Intent(callingActivity, TaskDetails.class);
//            goToAllTasks.putExtra(MyTasksActivity.MY_TASK_NAME, myTaskName);
//            goToAllTasks.putExtra(MyTasksActivity.MY_TASK_BODY, myTaskBody);
//            goToAllTasks.putExtra(String.valueOf(MyTasksActivity.MY_TASK_STATE), myTaskState);
//            callingActivity.startActivity(goToAllTasks);
//        });
//    }

    @Override
    public int getItemCount() {
        // TODO Step 1-10: (In RecyclerViewAdapter.getItemCount()) For testing purposes, hardcode a large number of items
//    return 100;
        // TODO Step 2-5: (In RecyclerViewAdapter.getItemCount()) Make the size of the list dynamic
        return allTasks.size();
    }

    // TODO Step 1-8: (In RecyclerViewAdapter) Make a ViewHolder class to hold a Fragment
    public static class MyTaskViewHolder extends RecyclerView.ViewHolder {
        public MyTaskViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
