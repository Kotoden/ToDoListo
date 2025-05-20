package com.example.todolisto.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todolisto.R;
import com.example.todolisto.data.Task;

import java.util.Calendar;

public class AddEditTaskActivity extends AppCompatActivity {

    public static final String EXTRA_TASK = "task";

    private EditText etTitle, etDesc;
    private Button btnPickDate, btnPickTime, btnSave;
    private Spinner spinnerPriority;
    private long deadlineMillis = 0;
    private Task editingTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        etTitle  = findViewById(R.id.etTitle);
        etDesc   = findViewById(R.id.etDescription);
        btnPickDate  = findViewById(R.id.btnPickDate);
        btnPickTime  = findViewById(R.id.btnPickTime);
        spinnerPriority = findViewById(R.id.spinnerPriority);
        btnSave   = findViewById(R.id.btnSave);

        Intent i = getIntent();
        if (i.hasExtra(EXTRA_TASK)) {
            editingTask = (Task) i.getSerializableExtra(EXTRA_TASK);
            etTitle.setText(editingTask.title);
            etDesc.setText(editingTask.description);
            deadlineMillis = editingTask.deadline;
            spinnerPriority.setSelection(editingTask.priority - 1);
        }

        btnPickDate.setOnClickListener(v -> pickDate());
        btnPickTime.setOnClickListener(v -> pickTime());
        btnSave.setOnClickListener(v -> saveAndReturn());
    }

    private void pickDate() {
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(this,
                (view, y, m, d) -> {
                    cal.set(y, m, d);
                    deadlineMillis = cal.getTimeInMillis();
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void pickTime() {
        Calendar cal = Calendar.getInstance();
        new TimePickerDialog(this,
                (view, h, min) -> {
                    cal.set(Calendar.HOUR_OF_DAY, h);
                    cal.set(Calendar.MINUTE, min);
                    deadlineMillis = cal.getTimeInMillis();
                },
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
        ).show();
    }

    private void saveAndReturn() {
        String title = etTitle.getText().toString().trim();
        if (title.isEmpty()) {
            Toast.makeText(this, "Введите заголовок", Toast.LENGTH_SHORT).show();
            return;
        }
        int pr = spinnerPriority.getSelectedItemPosition() + 1;
        Task t = (editingTask == null ? new Task() : editingTask);
        t.title = title;
        t.description = etDesc.getText().toString().trim();
        t.deadline = deadlineMillis;
        t.priority = pr;
        t.completed = (editingTask != null && editingTask.completed);

        Intent out = new Intent();
        out.putExtra("task", t);
        setResult(RESULT_OK, out);
        finish();
    }
}
