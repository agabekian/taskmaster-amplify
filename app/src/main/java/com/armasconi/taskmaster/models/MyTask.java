package com.armasconi.taskmaster.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

//TODO Step 4-3 Change model into an Entity
@Entity
public class MyTask {
    @PrimaryKey(autoGenerate = true)
    public Long id;
    private String title;
    // TODO Step: 4-2 edit the model class to reflect our enum
    private String body;
    private TaskStateEnum state;


    private java.util.Date datePosted;


    public MyTask(String title, String body, TaskStateEnum state, Date datePosted) {
        this.title = title;
        this.body = body;
        this.state = state;
        this.datePosted = datePosted;
    }

    public MyTask() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public TaskStateEnum getState() {
        return state;
    }

    public void setState(TaskStateEnum state) {
        this.state = state;
    }


    // TODO Step: 4-1 create the enum
    public enum TaskStateEnum {
        NEW("new"),
        COMPLETED("completed"),
        PENDING("pending");

        private final String taskState;

        TaskStateEnum(String taskState) {
            this.taskState = taskState;
        }

        public String getTaskState() {
            return taskState;
        }

        public static TaskStateEnum fromString(String selectedState) {
            for (TaskStateEnum state : TaskStateEnum.values()
            ) {
                if (state.taskState.equals(selectedState)) {
                    return state;
                }
            }
            return null;
        }

        @NonNull
        @Override
        public String toString() {
            if (taskState == null) {
                return "";
            }
            return taskState;
        }
    }
}