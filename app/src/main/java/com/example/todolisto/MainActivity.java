package com.example.todolisto;

import android.content.Intent;
import android.os.Bundle;
import com.example.todolisto.ui.AddEditTaskActivity;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolisto.data.Task;
import com.example.todolisto.ui.adapter.TaskAdapter;
import com.example.todolisto.viewmodel.TaskViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_ADD  = 1;
    private static final int REQ_EDIT = 2;

    private TaskViewModel vm;
    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupViewModel();
    }

    private void initViews() {
        RecyclerView rv = findViewById(R.id.rvTasks);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter();
        rv.setAdapter(adapter);

        // свайп для удаления
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
        ) {
            @Override
            public boolean onMove(RecyclerView rv, RecyclerView.ViewHolder vh, RecyclerView.ViewHolder t) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder vh, int dir) {
                Task t = adapter.getAt(vh.getAdapterPosition());
                vm.delete(t);
            }
        }).attachToRecyclerView(rv);

        // клики на элемент списка
        adapter.setListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onClick(Task t) {
                Intent i = new Intent(MainActivity.this, AddEditTaskActivity.class);
                i.putExtra("task", t);
                startActivityForResult(i, REQ_EDIT);
            }

            @Override
            public void onCheck(Task t, boolean isDone) {
                t.completed = isDone;
                vm.update(t);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(v -> {
            startActivityForResult(
                    new Intent(MainActivity.this, AddEditTaskActivity.class),
                    REQ_ADD
            );
        });
    }

    private void setupViewModel() {
        vm = new ViewModelProvider(this).get(TaskViewModel.class);
        vm.getAllTasks().observe(this, this::onTasksChanged);
    }

    private void onTasksChanged(List<Task> list) {
        adapter.setTasks(list);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Task t = (Task) data.getSerializableExtra("task");
            if (requestCode == REQ_ADD) {
                vm.insert(t);
            } else if (requestCode == REQ_EDIT) {
                vm.update(t);
            }
        }
    }
}
