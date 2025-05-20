package com.example.todolisto.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
    private static volatile AppDatabase inst;
    public static AppDatabase getInst(Context c) {
        if (inst == null) {
            inst = Room.databaseBuilder(
                    c.getApplicationContext(),
                    AppDatabase.class,
                    "todo_db"
            ).build();
        }
        return inst;
    }
}
