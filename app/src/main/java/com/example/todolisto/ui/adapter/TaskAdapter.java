package com.example.todolisto.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todolisto.R;
import com.example.todolisto.data.Task;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onClick(Task task);
        void onCheck(Task task, boolean isDone);
    }

    private final List<Task> tasks = new ArrayList<>();
    private OnItemClickListener listener;

    public void setListener(OnItemClickListener l) {
        listener = l;
    }

    public void setTasks(List<Task> list) {
        tasks.clear();
        tasks.addAll(list);
        notifyDataSetChanged();
    }

    public Task getAt(int pos) {
        return tasks.get(pos);
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        Task t = tasks.get(pos);
        holder.cbDone.setChecked(t.completed);
        holder.tvTitle.setText(t.title);
        holder.tvDeadline.setText(DateFormat.getDateTimeInstance().format(t.deadline));
        holder.tvPriority.setText(String.valueOf(t.priority));

        holder.cbDone.setOnCheckedChangeListener((button, isChecked) -> {
            if (listener != null) listener.onCheck(t, isChecked);
        });
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onClick(t);
        });
    }

    @Override public int getItemCount() {
        return tasks.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox cbDone;
        TextView tvTitle, tvDeadline, tvPriority;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            cbDone     = itemView.findViewById(R.id.cbDone);
            tvTitle    = itemView.findViewById(R.id.tvTitle);
            tvDeadline = itemView.findViewById(R.id.tvDeadline);
            tvPriority = itemView.findViewById(R.id.tvPriority);
        }
    }
}
