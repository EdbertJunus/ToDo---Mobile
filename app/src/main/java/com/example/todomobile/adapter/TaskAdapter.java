package com.example.todomobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todomobile.R;
import com.example.todomobile.TaskDetailActivity;
import com.example.todomobile.model.TaskItem;
import com.google.firebase.Timestamp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

//        Timestamp timestamp = (Timestamp) task.getTaskDateTime();
//
//        Date date = timestamp.toDate();
//        DateFormat df = new SimpleDateFormat("E, dd MMM, hh:mm aa");
//        String strDate = df.format(date);

        holder.tvTaskName.setText(task.getTaskName());
        holder.tvTaskTime.setText(task.getTaskDateTime());

        holder.cvTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, TaskDetailActivity.class);
                Log.d("papamu", task.getTaskId());
                intent.putExtra("taskId", task.getTaskId());
                intent.putExtra("taskName", task.getTaskName());
                intent.putExtra("taskDesc", task.getTaskDescription());
                intent.putExtra("taskDate", task.getTaskDateTime());
                ctx.startActivity(intent);
            }
        });
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
