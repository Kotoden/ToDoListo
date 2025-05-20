package com.example.todolisto.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY deadline")
    LiveData<List<Task>> getAll();
    @Insert void insert(Task t);
    @Update void update(Task t);
    @Delete void delete(Task t);
}
