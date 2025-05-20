package com.example.todolisto.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.todolisto.data.AppDatabase;
import com.example.todolisto.data.Task;
import com.example.todolisto.data.TaskDao;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskRepository {
    private final TaskDao dao;
    private final LiveData<List<Task>> allTasks;
    private final ExecutorService exec = Executors.newSingleThreadExecutor();

    public TaskRepository(Application app) {
        AppDatabase db = AppDatabase.getInst(app);
        dao = db.taskDao();
        allTasks = dao.getAll();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public void insert(Task t) {
        exec.execute(() -> dao.insert(t));
    }
    public void update(Task t) {
        exec.execute(() -> dao.update(t));
    }
    public void delete(Task t) {
        exec.execute(() -> dao.delete(t));
    }
}
