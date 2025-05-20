package com.example.todolisto.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "tasks")
public class Task implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String description;
    public long deadline;
    public int priority;
    public boolean completed;
}
