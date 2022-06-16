package com.example.todomobile.database;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todomobile.MainActivity;
import com.example.todomobile.RegisterActivity;
import com.example.todomobile.model.User;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class UserHelper {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String userId;

    public UserHelper() {
    }

    public void addNewUser(String userName, String userEmail, String userPassword, Context ctx){
        Map<String, Object> insertedData = new HashMap<>();
        insertedData.put("UserName", userName);
        insertedData.put("UserEmail", userEmail);
        insertedData.put("UserPassword", userPassword);

        db.collection("user")
                .document()
                .set(insertedData)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ctx, "Registration Success!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                ).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(ctx, "Registration Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
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

//        db.collection("user")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
//                            for (QueryDocumentSnapshot doc: task.getResult()){
//                                User user = new User(doc.getId(), doc.get("UserEmail").toString(),
//                                        doc.get("UserName").toString(), doc.get("UserPassword").toString());
//                                users.add(user);
//
//                            }
//                            firestoreCallback.onCallback(users);
//                        }else{
//                            Log.d("Gagal", "GAGAL");
//                        }
//                    }
//                });
        db.collection("user").orderBy("UserName", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.e("Firestore", error.getMessage());
                            return;
                        }

                        for(DocumentChange dc: value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                users.add(dc.getDocument().toObject(User.class));
                            }
                        }
                        //Adapter.notifyChanges();
                    }
                });
        return users;
    }

//    public interface FirestoreCallback{
//         void onCallback(Vector<User> userVector);
//    }


}
