package com.example.todomobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.todomobile.adapter.TaskAdapter;
import com.example.todomobile.model.TaskItem;

import java.util.ArrayList;
import java.util.Vector;

import static com.example.todomobile.model.GlobalVariable.taskList;

public class TaskActivity extends AppCompatActivity {
    private RecyclerView rvTask;
    private ImageView ivAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        init();
    }

    public void init() {
        rvTask = findViewById(R.id.task_rv_taskList);
        ivAddTask = findViewById(R.id.task_iv_addTask);

        rvTask.setAdapter(new TaskAdapter(this, taskList));
        rvTask.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        ivAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });
    }
}