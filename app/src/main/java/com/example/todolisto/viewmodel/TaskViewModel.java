package com.example.todolisto.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.todolisto.data.Task;
import com.example.todolisto.repository.TaskRepository;
import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private final TaskRepository repo;
    private final LiveData<List<Task>> allTasks;

    public TaskViewModel(@NonNull Application app) {
        super(app);
        repo = new TaskRepository(app);
        allTasks = repo.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }
    public void insert(Task t) {
        repo.insert(t);
    }
    public void update(Task t) {
        repo.update(t);
    }
    public void delete(Task t) {
        repo.delete(t);
    }
}
