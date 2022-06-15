package com.example.todomobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.todomobile.model.GlobalVariable.taskList;

public class TaskDetailActivity extends AppCompatActivity {
    private TextView tvTaskName, tvTaskDesc, tvTaskDate;
    private Button btnEditTask, btnRemoveTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        init();
    }

    public void init() {
        tvTaskName = findViewById(R.id.taskDetail_tv_taskName);
        tvTaskDesc = findViewById(R.id.taskDetail_tv_taskDesc);
        tvTaskDate = findViewById(R.id.taskDetail_tv_taskDate);
        btnEditTask = findViewById(R.id.taskDetail_btn_editTask);
        btnRemoveTask = findViewById(R.id.taskDetail_btn_removeTask);

        tvTaskName.setText(getIntent().getStringExtra("taskName"));
        tvTaskDesc.setText(getIntent().getStringExtra("taskDesc"));
        tvTaskDate.setText(getIntent().getStringExtra("taskDate"));

        btnEditTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnRemoveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < taskList.size(); i++) {
                    if(tvTaskName.getText().toString().equals(taskList.get(i).getTaskName())) {
                        // remove logic here - from Kevin
                        taskList.remove(i);

                        Intent intent = new Intent(TaskDetailActivity.this, TaskActivity.class);
                        startActivity(intent);
                        return;
                    }
                }
            }
        });
    }
}