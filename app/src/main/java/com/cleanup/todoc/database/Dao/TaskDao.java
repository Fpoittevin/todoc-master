package com.cleanup.todoc.database.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getTasks();

    @Insert
    long insertTask(Task task);

    @Delete
    long deleteTask(Task task);

    /*
        Dans le cours :

        @Query("DELETE FROM Item WHERE id = :itemId")
        int deleteItem(long itemId
     */
}
