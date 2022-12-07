package com.armasconi.taskmaster.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.armasconi.taskmaster.dao.TasksDao;
import com.armasconi.taskmaster.models.MyTask;


// TODO Step: 4-6 Enable converters
@TypeConverters({TaskMasterDatabaseConverters.class})

//TODO Step: 4-5 Setup the database!
@Database(entities = {MyTask.class}, version = 1) // if we update the version, it will delete the db!


public abstract class TaskMasterDatabase extends RoomDatabase {
    // TODO Step: 4-6 add your DAO's!
    public abstract TasksDao tasksDao();
}
