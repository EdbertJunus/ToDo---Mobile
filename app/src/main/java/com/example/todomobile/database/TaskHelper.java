package com.example.todomobile.database;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todomobile.model.TaskItem;
import com.example.todomobile.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class TaskHelper {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String TaskId;
    private String TaskName;
    private String TaskDescription;
    private DateTime TaskDateTime;
    private String UserId;

    public TaskHelper(String taskId) {
        TaskId = taskId;

    }

    public void addNewTask(String taskName, String taskDescription, DateTime taskDateTime, String userId){
        Map<String, Object> insertedData = new HashMap<>();
        insertedData.put("TaskName", TaskName);
        insertedData.put("TaskDescription", TaskDescription);
        insertedData.put("TaskDateTime", TaskDateTime);
        insertedData.put("UserId", UserId);

        db.collection("task")
                .document(TaskId)
                .set(insertedData);
    }

    public void updateTask(String key, Object value){
        Map<String, Object> toBeUpdated = new HashMap<>();
        toBeUpdated.put(key, value);

        db.collection("task")
                .document(TaskId)
                .set(toBeUpdated);
    }

    public Vector<TaskItem> showTaskList(){
        Vector<TaskItem> tasks = new Vector<>();

//        db.collection("task")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
//                            for (QueryDocumentSnapshot doc: task.getResult()){
//
//                                TaskItem newTask = new TaskItem(doc.getId(), doc.get("TaskName").toString(),
//                                        doc.get("TaskDescription").toString(), doc.get("TaskDate").toString(),
//                                        doc.get("UserId").toString());
//                                tasks.add(newTask);
//                            }
//                        }
//                    }
//                });

        db.collection("task").orderBy("UserName", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.e("Firestore", error.getMessage());
                            return;
                        }

                        for(DocumentChange dc: value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                tasks.add(dc.getDocument().toObject(TaskItem.class));
                            }
                        }
                        //Adapter.notifyChanges();
                    }
                });

        return tasks;
    }

    public void removeTask(){
        db.collection("task")
                .document(TaskId)
                .delete();
    }

}
