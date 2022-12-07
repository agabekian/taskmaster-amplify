package com.armasconi.taskmaster.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.armasconi.taskmaster.models.MyTask;


import java.util.List;

// TODO STEP: 4-4 Create a DAO interface for your model
@Dao // Think of this like a Spring JPA repository, but we have to implement more stuff ourselves
public interface TasksDao {
  @Insert
  public long insertTask(MyTask task);

  //find all
  @Query("SELECT * FROM MyTask")
  public List<MyTask> findAll();

  //findById
  @Query("SELECT * FROM MyTask WHERE id = :id")
  public MyTask findById(long id);

  // findAllByType
  @Query("SELECT * FROM MyTask WHERE state = :state")
  public List<MyTask> findAllByState(MyTask.TaskStateEnum state);

  @Delete
  public void delete(MyTask task);

  @Update
  public void update(MyTask task);
}
