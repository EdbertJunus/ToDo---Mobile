package com.example.todomobile.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.todomobile.MainActivity;
import com.example.todomobile.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class UserHelper {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String userId;
    private String userEmail;
    private String userName;
    private String userPassword;

    public UserHelper(String userId, String userEmail, String userName, String userPassword) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public void addNewUser(){
        Map<String, Object> insertedData = new HashMap<>();
        insertedData.put("UserName", userName);
        insertedData.put("UserEmail", userEmail);
        insertedData.put("UserPassword", userPassword);

        db.collection("user")
                .document(userId)
                .set(insertedData);
    }

    public void updateUserData(String key, Object value){
        Map<String, Object> toBeUpdated = new HashMap<>();
        toBeUpdated.put(key, value);

        db.collection("user")
                .document(userId)
                .set(toBeUpdated);
    }

    public Vector<User> showUserList(){
        Vector<User> users = new Vector<>();

        db.collection("user")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot doc: task.getResult()){
                                User user = new User(doc.getId(), doc.get("UserName").toString(),
                                        doc.get("UserEmail").toString(), doc.get("UserPassword").toString());
                                users.add(user);
                            }
                        }
                    }
                });

        return users;
    }

}
