package com.example.todomobile.database;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todomobile.model.GlobalVariable;
import com.example.todomobile.model.TaskItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Observer;
import java.util.Vector;

public class TaskHelper {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String TaskId;
    private String TaskName;
    private String TaskDescription;
    private String TaskDateTime;
    private String UserId;

    public void addNewTask(String taskName, String taskDescription, String taskDateTime, String userId) {
        Map<String, Object> insertedData = new HashMap<>();
        insertedData.put("TaskName", taskName);
        insertedData.put("TaskDescription", taskDescription);
        insertedData.put("TaskDateTime", taskDateTime);
        insertedData.put("UserId", userId);

        db.collection("task")
                .document()
                .set(insertedData);
    }

    public void updateTask(String TaskId, String TaskName, String TaskDescription, String TaskDateTime) {
//        Map<String, Object> toBeUpdated = new HashMap<>();
//        toBeUpdated.put("TaskName", TaskName);
//        toBeUpdated.put("TaskDescription", TaskDescription);
//        toBeUpdated.put("TaskDateTime", TaskDateTime);
//        toBeUpdated.put("UserId", GlobalVariable.loggedUser.getUserId());

        DocumentReference documentReference =
                db.collection("task")
                .document(TaskId);

        documentReference.update("TaskName", TaskName, "TaskDescription", TaskDescription, "TaskDateTime", TaskDateTime).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful()) {
////                    Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
//                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

//        db.collection("task")
//                .document(TaskId)
//                .set(toBeUpdated);
    }

    public Vector<TaskItem> showTaskList() {
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
                        if (error != null) {
                            Log.e("Firestore", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                tasks.add(dc.getDocument().toObject(TaskItem.class));
                            }
                        }
                        //Adapter.notifyChanges();
                    }
                });

        return tasks;
    }

    public void removeTask() {
        db.collection("task")
                .document(TaskId)
                .delete();
    }

}
