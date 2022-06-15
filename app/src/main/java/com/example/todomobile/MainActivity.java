package com.example.todomobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.todomobile.database.UserHelper;
import com.example.todomobile.model.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
//    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserHelper userHelper = new UserHelper("AdminId");
        Vector<User> users = userHelper.showUserList();
//        userHelper.showUserList( new UserHelper.FirestoreCallback(){
//            @Override
//            public void onCallback(Vector<User> userVector) {
//                for (User user : userVector) {
//                    users.add(user);
//                    Log.d("Data", user.getUserName());
//                }
//            }
//        });

        Integer totalUser = users.size();
        System.out.println("Total User " + totalUser);

        for (User user : users) {
            Log.d("Data di Loop", user.getUserName());
        }

    }
}