package com.example.todomobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todomobile.R;
import com.example.todomobile.model.TaskItem;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private Context ctx;
    private ArrayList<TaskItem> taskList;

    public TaskAdapter(Context ctx, ArrayList<TaskItem> taskList) {
        this.ctx = ctx;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_task, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {
        TaskItem task = taskList.get(position);

        holder.tvTaskName.setText(task.getTaskName());
        holder.tvTaskTime.setText(task.getTaskDateTime());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected CardView cvTask;
        protected CheckBox cbCheck;
        protected TextView tvTaskName, tvTaskTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cvTask = itemView.findViewById(R.id.task_cv_task);
            cbCheck = itemView.findViewById(R.id.task_cb_check);
            tvTaskName = itemView.findViewById(R.id.task_tv_taskName);
            tvTaskTime = itemView.findViewById(R.id.task_tv_taskTime);
        }
    }
}
