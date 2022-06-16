package com.example.todomobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todomobile.adapter.TaskAdapter;
import com.example.todomobile.model.TaskItem;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TaskActivity extends AppCompatActivity {
    private RecyclerView rvTask;
    private ImageView ivAddTask;

    private ProgressDialog progressDialog;
    private ArrayList<TaskItem> taskList = new ArrayList<>();
    private TaskAdapter taskAdapter = new TaskAdapter(this, taskList);

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        init();
    }

    public void init() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Getting your tasks ready...");
        progressDialog.show();

        rvTask = findViewById(R.id.task_rv_taskList);
        ivAddTask = findViewById(R.id.task_iv_addTask);

        db = FirebaseFirestore.getInstance();

        rvTask.setAdapter(taskAdapter);
        rvTask.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        ivAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });

        eventChangeListener();
    }

    public void eventChangeListener() {
        db.collection("task")
                .orderBy("TaskName")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Log.e("Firestore Error", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                taskList.add(dc.getDocument().toObject(TaskItem.class));
                                Log.d("yoho", dc.getDocument().toObject(TaskItem.class).getTaskId() + "");
                                Log.d("yoho", dc.getDocument().toObject(TaskItem.class).getTaskName() + "");
                                Log.d("yoho", dc.getDocument().toObject(TaskItem.class).getTaskDescription() + "");
                                Log.d("yoho", dc.getDocument().toObject(TaskItem.class).getTaskDateTime() + "");
                                Log.d("yoho", dc.getDocument().toObject(TaskItem.class).getUserId() + "");
                                Log.d("yoho", dc.getDocument() + "");
                            }
                            taskAdapter.notifyDataSetChanged();
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        }
                    }
                });
    }
}